package com.example.myprojectteam7.old

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.myprojectteam7.Date
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class CalendarRepository(key: String?) {
    val database = Firebase.database
    val userRef = database.getReference(key ?: "bug")

    fun observeIndex(idx: MutableLiveData<HashMap<String,String>>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var temp = HashMap<String,String>()
                for(i in snapshot.children) {
                    Log.d("스냅샷1",i.toString())
                    Log.d("스냅샷2",i.key.toString())
                    Log.d("스냅샷3",i.value.toString())
                    temp.put(i.key.toString(),i.value.toString())
                }
                idx.postValue(temp)
            }
            override fun onCancelled(error: DatabaseError) {
                /*
                var temp = HashMap<String,Int>()
                temp.put("none", 0)
                idx.postValue(temp)
                 */
            }
        })
    }

    fun observeView(view: MutableLiveData<View>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.child("View").getValue<View>()
                Log.d("뷰테스트",temp.toString())
                view.postValue(temp)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun postView(post: View) {
        //중복검사
        /*
        val key = userRef.child("user-posts").child("cdf123").key
        Log.d("뷰 키확인", key.toString())
        if (key == null) {
            Log.d("뷰 키중복", key.toString())
            return
        }
         */

        //val post = View(year, month, day, index)
        val postValues = post.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/View" to postValues
        )

        userRef.updateChildren(childUpdates)
    }

    fun postIdx(key: String, newValue: String) {
        val userVal = userRef.child(key)
        userVal.setValue(newValue)
    }

    fun observeYear(year: MutableLiveData<String>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                year.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun postYear(newValue: String) {
        userRef.setValue(newValue)
    }

    //Date를 읽고 한달로 -> 리사이클러뷰
    fun observeViewDate(view: MutableLiveData<ArrayList<Date>>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val viewDate = snapshot.child("viewDate")
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE)
                val firstday = LocalDate.of(date.year,date.monthValue,1)
                val firstdayOfWeek = if(firstday.dayOfWeek.value!=7) firstday.dayOfWeek.value else 0
                val startdate = firstday.minusDays(firstdayOfWeek.toLong())
                val Dates = ArrayList<Date>()
                for(i in 0.. 41) {
                    val temp = startdate.plusDays(i.toLong())
                    Dates.add(Date(temp))
                }
                view.postValue(Dates)
                /*
                val viewshot = snapshot.child("viewDate")
                val indexshot = snapshot.child("Calendar")
                var temp = ArrayList<Date>()
                Log.d("스냅샷1",viewshot.value.toString())
                val date = LocalDate.parse(viewshot.value.toString(), DateTimeFormatter.ISO_DATE)
                Log.d("스냅샷2",date.toString())
                view.postValue(temp)
                 */
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}