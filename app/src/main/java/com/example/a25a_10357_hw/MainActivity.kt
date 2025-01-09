package com.example.a25a_10357_hw

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a25a_10357_hw.interfaces.TiltCallback
import com.example.a25a_10357_hw.logic.GameManager
import com.example.a25a_10357_hw.utilities.Constants
import com.example.a25a_10357_hw.utilities.SignalManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.example.a25a_10357_hw.logic.GameObjectManager
import com.example.a25a_10357_hw.logic.Cowgirl
import com.example.a25a_10357_hw.logic.Coin
import com.example.a25a_10357_hw.utilities.SingleSoundPlayer
import com.example.a25a_10357_hw.utilities.TiltDetector
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationRequest



class MainActivity : AppCompatActivity() {

    var COW_ROW = 6
    private lateinit var main_BTN_left: MaterialButton
    private lateinit var main_BTN_right: MaterialButton
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>
    private lateinit var main_LBL_score: MaterialTextView

    private lateinit var gameManager: GameManager
    private lateinit var gameObjectManager: GameObjectManager
    private lateinit var tiltDetector: TiltDetector
    private var CowcurrentColumn = 2

    private lateinit var cowgirlViews: Array<Array<AppCompatImageView>>
    private lateinit var cow: AppCompatImageView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private var currentLocation: Location? = null
    private lateinit var locationCallback: LocationCallback


    private var gameSpeed: Long = 700
    private var isSensorMode = false
    private var isHardMode = false
    private var gameHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        startLocationUpdates()


        initTiltDetector()
        isSensorMode = intent.getBooleanExtra("CONTROL_TYPE", false)
        isHardMode = intent.getBooleanExtra("DIFFICULTY", false)
        gameSpeed = if (isHardMode) 400 else 600
        findViews()
        gameManager = GameManager(main_IMG_hearts.size)
        gameObjectManager = GameObjectManager(
            this,
            cowgirlViews,
            gameManager,
            main_IMG_hearts,
            COW_ROW
        )
        initViews()
        initCow()
        updateCowPosition()
        startGameMovement()
    }


    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationRequest = LocationRequest.Builder(5000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let {
                        currentLocation = it
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // LOCATION_PERMISSION
                } else {
                    SignalManager.getInstance().toast(
                        "Location permission is required for saving high scores with location"
                    )
                }
            }
        }
    }



    private fun findViews() {
        main_LBL_score = findViewById(R.id.main_LBL_score)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart3),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart1)
        )
        main_BTN_left = findViewById(R.id.main_BTN_left)
        main_BTN_right = findViewById(R.id.main_BTN_right)

        cowgirlViews = arrayOf(
            arrayOf(
                findViewById(R.id.cowgirl_0_0), findViewById(R.id.cowgirl_0_1),
                findViewById(R.id.cowgirl_0_2), findViewById(R.id.cowgirl_0_3),
                findViewById(R.id.cowgirl_0_4)
            ),
            arrayOf(
                findViewById(R.id.cowgirl_1_0), findViewById(R.id.cowgirl_1_1),
                findViewById(R.id.cowgirl_1_2), findViewById(R.id.cowgirl_1_3),
                findViewById(R.id.cowgirl_1_4)
            ),
            arrayOf(
                findViewById(R.id.cowgirl_2_0), findViewById(R.id.cowgirl_2_1),
                findViewById(R.id.cowgirl_2_2), findViewById(R.id.cowgirl_2_3),
                findViewById(R.id.cowgirl_2_4)
            ),
            arrayOf(
                findViewById(R.id.cowgirl_3_0), findViewById(R.id.cowgirl_3_1),
                findViewById(R.id.cowgirl_3_2), findViewById(R.id.cowgirl_3_3),
                findViewById(R.id.cowgirl_3_4)
            ),
            arrayOf(
                findViewById(R.id.cowgirl_4_0), findViewById(R.id.cowgirl_4_1),
                findViewById(R.id.cowgirl_4_2), findViewById(R.id.cowgirl_4_3),
                findViewById(R.id.cowgirl_4_4)
            ),
            arrayOf(
                findViewById(R.id.cowgirl_5_0), findViewById(R.id.cowgirl_5_1),
                findViewById(R.id.cowgirl_5_2), findViewById(R.id.cowgirl_5_3),
                findViewById(R.id.cowgirl_5_4)
            ),
            arrayOf(
                findViewById(R.id.cowgirl_6_0), findViewById(R.id.cowgirl_6_1),
                findViewById(R.id.cowgirl_6_2), findViewById(R.id.cowgirl_6_3),
                findViewById(R.id.cowgirl_6_4)
            ),
            arrayOf(
                findViewById(R.id.cowgirl_7_0), findViewById(R.id.cowgirl_7_1),
                findViewById(R.id.cowgirl_7_2), findViewById(R.id.cowgirl_7_3),
                findViewById(R.id.cowgirl_7_4)
            )
        )
    }

    private fun initViews() {
        main_LBL_score.text = gameManager.score.toString()
        if (isSensorMode) {
            main_BTN_left.visibility = View.INVISIBLE
            main_BTN_right.visibility = View.INVISIBLE
        } else {
            main_BTN_left.visibility = View.VISIBLE
            main_BTN_right.visibility = View.VISIBLE
            main_BTN_left.setOnClickListener { moveCowLeft() }
            main_BTN_right.setOnClickListener { moveCowRight() }
        }
        for (row in cowgirlViews) {
            for (view in row) {
                view.visibility = View.INVISIBLE
            }
        }
    }

    private fun initCow() {
        cow = cowgirlViews[COW_ROW][CowcurrentColumn].apply {
            setImageResource(R.drawable.cow)
            visibility = View.VISIBLE
        }
    }

    private fun moveGameObjects() {
        val isGameOver = gameObjectManager.moveObjects(CowcurrentColumn)

        if (isGameOver) {
            endGame()
            return
        }

        if (shouldAddNewObjects()) {
            if ((0..100).random() < 50) {
                gameObjectManager.addNewObject(Coin())
            } else {
                gameObjectManager.addNewObject(Cowgirl())
            }
        }

        ensureCowVisibility()
        updateScore()
    }

    private fun shouldAddNewObjects(): Boolean {
        return gameObjectManager.objectsCount() < 4 && (0..100).random() < 55
    }

    private fun moveCowLeft() {
        if (CowcurrentColumn > 0) {
            CowcurrentColumn--
            updateCowPosition()
            checkCollisions()
        }
    }

    private fun moveCowRight() {
        if (CowcurrentColumn < 4) {
            CowcurrentColumn++
            updateCowPosition()
            checkCollisions()
        }
    }

    private fun checkCollisions() {
        if (gameObjectManager.checkCollisions(CowcurrentColumn)) {
            endGame()
        }
        updateScore()
    }

    private fun updateCowPosition() {
        val cowViews = cowgirlViews[COW_ROW]
        cowViews.forEach { it.visibility = View.INVISIBLE }
        cowViews[CowcurrentColumn].visibility = View.VISIBLE
        setCowImage(cowViews[CowcurrentColumn])
    }

    private fun ensureCowVisibility() {
        cowgirlViews[COW_ROW][CowcurrentColumn].apply {
            setImageResource(R.drawable.cow)
            visibility = View.VISIBLE
        }
    }

    private fun setCowImage(imageView: AppCompatImageView) {
        imageView.setImageResource(R.drawable.cow)
    }

    private fun updateScore() {
        main_LBL_score.text = gameManager.score.toString()
    }

    private fun endGame() {
        gameHandler?.removeCallbacksAndMessages(null)
        gameHandler = null
        gameObjectManager.clear()

        currentLocation?.let { location ->
            gameManager.saveHighScore(location.latitude, location.longitude)
        } ?: run {
            gameManager.saveHighScore(0.0, 0.0)
        }

        changeActivity("Game Over!")
    }




    private fun changeActivity(message: String) {
        val intent = Intent(this, GameOverActivity::class.java)
        val bundle = Bundle()
        bundle.putString(Constants.BundleKeys.STATUS_KEY, message)
        bundle.putInt(Constants.BundleKeys.SCORE_KEY, gameManager.score)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun startGameMovement() {
        gameHandler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                moveGameObjects()
                gameHandler?.postDelayed(this, gameSpeed)
            }
        }
        gameHandler?.post(runnable)
    }

    private fun initTiltDetector() {
        tiltDetector = TiltDetector(
            context = this,
            tiltCallback = object : TiltCallback {
                override fun tiltX(value: Float) {
                    if (isSensorMode) {
                        if (value > 0) {
                            moveCowLeft()
                        } else {
                            moveCowRight()
                        }
                    }
                }

                override fun tiltY(value: Float) {
                    val minSpeed = if (isHardMode) 250L else 500L
                    val maxSpeed = if (isHardMode) 600L else 900L
                    gameSpeed = (gameSpeed + (value * 50)).toLong().coerceIn(minSpeed, maxSpeed)
                    updateGameSpeed()
                }
            }
        )
    }

    private fun updateGameSpeed() {
        gameHandler?.removeCallbacksAndMessages(null)
        startGameMovement()
    }

    override fun onResume() {
        super.onResume()
        if (isSensorMode) {
            tiltDetector.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isSensorMode) {
            tiltDetector.stop()
        }
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}