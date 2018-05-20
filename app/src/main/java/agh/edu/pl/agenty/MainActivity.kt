package agh.edu.pl.agenty

import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings.Secure
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

import kotlinx.android.synthetic.main.activity_main.tab
import kotlinx.android.synthetic.main.activity_main.view_pager as viewPager

class MainActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_RECORD_AUDIO = 555

    private val android_id by lazy { Secure.getString(this.contentResolver, Secure.ANDROID_ID) }

    private lateinit var phoneVoiceRef: DatabaseReference
    private lateinit var allVoicesRef: DatabaseReference
    private lateinit var observable: Disposable
    private lateinit var soundMatter: SoundMatter
    private lateinit var database: FirebaseDatabase

    private lateinit var pageAdapter: VoicePageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance()

        allVoicesRef = database.getReference("Voices")
        phoneVoiceRef = allVoicesRef.child("id=$android_id")

        requestAudioPermissions()

        pageAdapter = VoicePageAdapter(supportFragmentManager)

        initViewPager()
    }

    private fun initViewPager() {
        viewPager.adapter = pageAdapter
        tab.setCustomTabView({ container, position, _ ->
            val inflater = LayoutInflater.from(container.context)
            val icon = inflater.inflate(R.layout.view_pager_item, container, false) as ImageView
            icon.setImageDrawable(getDrawable(VoiceFragments.values()[position].drawableRes))

            return@setCustomTabView icon
        })
        tab.setViewPager(viewPager)
    }

    private fun recordAudio() {
        observable = Observable.interval(1, TimeUnit.SECONDS)
                .map { soundMatter.amplitude }
                .buffer(5)
                .map { it.average() }
                .subscribe {
                    phoneVoiceRef.setValue(it)
                    Log.d("TEST", it.toString())
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        observable.dispose()
    }

    private fun requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                        RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show()

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        arrayOf(RECORD_AUDIO),
                        MY_PERMISSIONS_RECORD_AUDIO)

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        arrayOf(RECORD_AUDIO),
                        MY_PERMISSIONS_RECORD_AUDIO)
            }
        } else if (ContextCompat.checkSelfPermission(this,
                        RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now
            soundMatter = SoundMatter()
            soundMatter.start()
            recordAudio()
        }//If permission is granted, then go ahead recording audio
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_RECORD_AUDIO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    soundMatter = SoundMatter()
                    soundMatter.start()
                    recordAudio()
                } else {
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

}
