package com.example.note_kotlin_project.service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.note_kotlin_project.R
import com.example.note_kotlin_project.activity.idLichHoc
import com.example.note_kotlin_project.activity.thu
import com.example.note_kotlin_project.database.SQLiteHelper
import com.example.note_kotlin_project.dataclass.MonHoc
import com.example.note_kotlin_project.dataclass.ThongBao
import java.text.SimpleDateFormat
import java.util.*

class MyBackgroundService : Service() {
    val sql: SQLiteHelper = SQLiteHelper(this)
    var arrayTB : ArrayList<ThongBao> = ArrayList()
    private val CHANNEL_ID ="channel_id_example_01"
    private val notificationId = 101
    private val CHANNEL_ID2 ="channel_id_example_02"
    private val notificationId2 = 102
    companion object {
        private const val TAG = "MyBackgroundService"
        //private const val DELAY_MILLIS = 1 * 60 * 1000L // 1 phút
    }

    private val handler = Handler()
    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    private val doTask = object : Runnable {
        override fun run() {
            checkTimeGC("14") // Thời gian nhập vào (ví dụ)
            checkTimeMH("14")
            createNotificationChannel()
            createNotificationChannelMH()
            handler.postDelayed(this, 60 * 60 * 1000L) // Kiểm tra sau mỗi 1h
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service started")

        // Bắt đầu in thời gian sau mỗi 1 phút
        handler.post(doTask)


        // Trả về kiểu khởi động lại, ví dụ START_STICKY
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service destroyed")

        // Dừng việc in thời gian khi service bị hủy
        handler.removeCallbacks(doTask)
    }

    private fun checkTimeGC(inputTime: String) {
        try {
            val currentTime = Calendar.getInstance().time
            Log.d(TAG, currentTime.hours.toString() +" - "+ inputTime)
            if (inputTime != null && inputTime.equals(currentTime.hours.toString())) {
                Log.d(TAG, "Thời gian đúng. Ghi log hoặc thực hiện hành động mong muốn.")
                ///
                val cal = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = dateFormat.format(cal)
                arrayTB = getThongBaoHomNay(currentDate)
                var s:String =""
                for(i in arrayTB){
                    s = s + i.tenTB + "\n"
                }
                if (s==""){
                    sendNotification("Hôm nay bạn không có thông báo nào")
                }else{
                    sendNotification(s)
                }

            } else {
                Log.d(TAG, "Thời gian không đúng.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Lỗi khi kiểm tra thời gian.")
        }
    }
    private fun checkTimeMH(inputTime: String) {
        try {
            val currentTime = Calendar.getInstance().time
            Log.d(TAG, currentTime.hours.toString() +" - "+ inputTime)
            if (inputTime != null && inputTime.equals(currentTime.hours.toString())) {
                Log.d(TAG, "Thời gian đúng. Ghi log hoặc thực hiện hành động mong muốn.")
                var thu_nm = getDayOfWeekNumber()
                if(thu_nm == 8){
                    thu_nm =2
                }else{
                    thu_nm++
                }
                idLichHoc = getID_truycapnhanh()
                var arrayMH : ArrayList<MonHoc> = ArrayList()
                //arrayMH.add(MonHoc("Lâp trình Android"))

                arrayMH = sql.getAllMonHoc(idLichHoc,thu_nm)
                var s:String =""
                for(i in arrayMH){
                    s = s + i.tenMH + "\n"
                }

                if (s==""){
                    sendNotificationMH("Ngày mai bạn không có môn học nào")
                }else{
                    sendNotificationMH(s)
                }
            } else {
                Log.d(TAG, "Thời gian không đúng.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Lỗi khi kiểm tra thời gian.")
        }
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name ="Notification Title"
            val descriptionText ="Notification Description"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotification(s: String){
        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Note")
            .setContentText("Các thông báo ngày hôm nay")
            .setStyle(NotificationCompat.BigTextStyle().bigText(s))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }
    private fun createNotificationChannelMH(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name ="Notification Title"
            val descriptionText ="Notification Description"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel = NotificationChannel(CHANNEL_ID2,name,importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotificationMH(s: String){
        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Note")
            .setContentText("Các môn học ngày mai")
            .setStyle(NotificationCompat.BigTextStyle().bigText(s))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId2, builder.build())
        }
    }
    fun getThongBaoHomNay(today: String):ArrayList<ThongBao>{
        return sql.getAllThongBaoToDay(today)
    }
    fun getID_truycapnhanh():Int{
        val sharedPreferences = getSharedPreferences("id_lichhoc", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id_lichhoc", -1)
        return id
    }
    fun getDayOfWeekNumber(): Int {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Chuyển đổi từ thứ hai - thứ bảy sang số thứ tự
        val dayNumber = if (dayOfWeek == Calendar.SUNDAY) {
            8 // Chủ Nhật
        } else {
            dayOfWeek
        }
        return dayNumber
    }
}