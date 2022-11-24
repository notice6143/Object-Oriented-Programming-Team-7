package com.example.myprojectteam7.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.myprojectteam7.*
import com.example.myprojectteam7.Date
import com.example.myprojectteam7.Todo
import com.example.myprojectteam7.ViewCalendar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class CalendarsRepository(key: String?) {
    val database = Firebase.database
    val dataRef = database.reference
    val userRef = database.getReference(key ?: "bug")
    val phone = key?.split("/")?.get(1) ?: ""

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

    //현재 유저의 Date 읽기
    fun observeDate(view: MutableLiveData<LocalDate>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val viewDate = snapshot.child("viewDate")
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE)
                view.postValue(date)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    //viewDate 쓰기
    fun postDate(newDate: LocalDate) {
        userRef.child("viewDate").setValue(newDate.toString())
        //val title = "일정1입니다"
        //userRef.child("Test").child("view").setValue("${phone}_${title}_${newDate}")
    }

    //일정 한 개 읽기
    //Todo 수정필요
    fun observeTodo(view: MutableLiveData<Todo>) {
        dataRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val todoSnap = snapshot.child("User-todolists").child(phone)
                for(i in todoSnap.children) {
                    val temp = i.getValue<Todo>()
                    Log.d("일정한개", temp.toString())
                }
                //view.postValue(temp)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    //일정 여러 개 읽기 -> 리사이클러뷰 todolist
    //dataRef -> /
    //일정목록경로 -> User-todolists/사용자아이디/yyyy-mm/key
    //Todo yym? yymd? 둘 중 하나 선택
    fun observeViewTodolist(view: MutableLiveData<ArrayList<Todo>>) {
        dataRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val viewDate = snapshot.child("Users").child(phone).child("viewDate")   //Users/phone/viewDate
                Log.d("스냅뷰데이트",viewDate.toString())
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE) //LocalDate
                val yymd = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
                val viewTodo = snapshot.child("User-todolists").child(phone).child(yymd)

                val todolist = ArrayList<Todo>()
                for(i in viewTodo.children) {
                    Log.d("스냅샷i",i.value.toString())
                    val map = i.value as Map<String, Any?>
                    val uid = map.get("uid") as String
                    val title = map.get("title") as String
                    val dateStr = map.get("date") as String
                    val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
                    val memo = map.get("memo") as String
                    val temp = Todo(uid,title,date,memo)
                    Log.d("스냅샷todo",temp.toString())
                    todolist.add(temp)
                }
                Log.d("최종스냅todo",todolist.toString())
                view.postValue(todolist)
                /*
                val temp = snapshot.child("MyTodoList").getValue<ArrayList<Todo>>()
                Log.d("일정여러개",temp.toString())
                view.postValue(temp)*/
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    //Date-Todo해쉬맵
    //Date하나에-일정여러개 매핑 -> 리사이클러뷰
    fun observeMapDateTodo(view: MutableLiveData<HashMap<Date,ArrayList<Todo>>>) {
        dataRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val viewDate = snapshot.child("Users").child(phone).child("viewDate")   //Users/phone/viewDate
                Log.d("스냅맵",viewDate.toString())
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE) //LocalDate

                val dateTodo = HashMap<Date,ArrayList<Todo>>()

                val firstday = LocalDate.of(date.year,date.monthValue,1)
                val firstdayOfWeek = if(firstday.dayOfWeek.value!=7) firstday.dayOfWeek.value else 0
                val startdate = firstday.minusDays(firstdayOfWeek.toLong())
                for(dt in 0.. 41) {
                    val temp = startdate.plusDays(dt.toLong())   //변하는 Date
                    Log.d("일정변화",temp.toString())
                    //todolist추가
                    val viewTodo = snapshot.child("User-todolists").child(phone).child(temp.toString())
                    val todolist = ArrayList<Todo>()
                    for(td in viewTodo.children) {
                        Log.d("스냅맵j",td.value.toString())
                        val map = td.value as Map<String, Any?>
                        val uid = map.get("uid") as String
                        val title = map.get("title") as String
                        val dateStr = map.get("date") as String
                        val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
                        val memo = map.get("memo") as String
                        val temp2 = Todo(uid,title,date,memo)
                        Log.d("스냅맵todo",temp2.toString())
                        todolist.add(temp2)
                    }

                    dateTodo.put(Date(temp),todolist)
                }

                Log.d("최종스냅todo",dateTodo.toString())
                view.postValue(dateTodo)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    //Date-Todolist 객체
    //Date하나에-일정여러개 매핑 -> 리사이클러뷰
    fun observeCalendar(view: MutableLiveData<ArrayList<ViewCalendar>>) {
        dataRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val viewDate = snapshot.child("Users").child(phone).child("viewDate")   //Users/phone/viewDate
                Log.d("스냅맵",viewDate.toString())
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE) //LocalDate

                val dateTodo = ArrayList<ViewCalendar>()

                val firstday = LocalDate.of(date.year,date.monthValue,1)
                val firstdayOfWeek = if(firstday.dayOfWeek.value!=7) firstday.dayOfWeek.value else 0
                val startdate = firstday.minusDays(firstdayOfWeek.toLong())
                for(dt in 0.. 41) {
                    val temp = startdate.plusDays(dt.toLong())   //변하는 Date
                    Log.d("일정변화",temp.toString())
                    //todolist추가
                    val viewTodo = snapshot.child("User-todolists").child(phone).child(temp.toString())
                    val todolist = ArrayList<Todo>()
                    for(td in viewTodo.children) {
                        Log.d("스냅맵j",td.value.toString())
                        val map = td.value as Map<String, Any?>
                        val uid = map.get("uid") as String
                        val title = map.get("title") as String
                        val dateStr = map.get("date") as String
                        val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
                        val memo = map.get("memo") as String
                        val temp2 = Todo(uid,title,date,memo)
                        Log.d("스냅맵todo",temp2.toString())
                        todolist.add(temp2)
                    }
                    dateTodo.add(ViewCalendar(temp,todolist))
                }

                Log.d("최종스냅todo",dateTodo.toString())
                view.postValue(dateTodo)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    //새로운 일정 업데이트
    //newTodo(userId, title, Date, memo)
    fun postTodo(newTodo: Todo) {
        /*
        //yym -> "20xx-xx"
        val yym = newTodo.date?.format(DateTimeFormatter.ofPattern("yyyy-MM")).toString()*/

        //yymd -> "20xx-xx-xx"
        val yymd = newTodo.date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()

        //고유의 키 값 생성 후 push ex)-NHeffgDmkuygkDe4Mkb
        //1. key 생성 2. 객체 매핑 3. 해당경로/key 업데이트
        val key = userRef.child("MyTodoList/$yymd").push().key
        if (key == null) {
            Log.w("키 널 로그", "Couldn't get push key for Todo")
            return
        }

        val postValues = newTodo.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/Users/$phone/MyTodoList/$yymd/$key" to postValues,
            "/User-todolists/$phone/$yymd/$key" to postValues
        )
        dataRef.updateChildren(childUpdates)

        /*
        //경로 설정, userRef.child("MyTodolist").child(key) -> Users/$phone/MyTodolist/$key
        //userRef -> Users/$phone
        val childUpdates = hashMapOf<String, Any>(
            "/MyTodoList/$yym/$key" to postValues,
            "/user-todolists/$phone/$key" to postValues
        )
        userRef.updateChildren(childUpdates)*/
    }

    /*fun writeNewPost(userId: String, username: String, title: String, body: String) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        val key = database.child("posts").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return
        }

        val post = Post(userId, username, title, body)
        val postValues = post.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/posts/$key" to postValues,
            "/user-posts/$userId/$key" to postValues
        )

        database.updateChildren(childUpdates)
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
    }*/
}