package com.example.myprojectteam7.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
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
    //dataRef: 루트, userRef: 유저
    val dataRef = database.reference
    val userRef = database.getReference(key ?: "bug")
    val phone = key?.split("/")?.get(1) ?: ""

    //Todo 현재날짜
    //현재 유저가 가르키고 있는 Date 읽기
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


    //viewDate 쓰기 -> 유저의 Date 포인터
    fun postViewDate(newDate: LocalDate) {
        userRef.child("viewDate").setValue(newDate.toString())
    }

    //Todo 일정

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


                //42칸 달력 표시
                for(dt in 0.. 41) {
                    val temp = startdate.plusDays(dt.toLong())   //변하는 Date
                    Log.d("일정변화",temp.toString())

                    //todolist추가
                    //친구는 관계에서 번호를 가져온 후에 반복문 한번 더
                    val viewTodo = snapshot.child("User-todolists").child(phone).child(temp.toString())
                    val todolist = ArrayList<Todo>()
                    //Me
                    for(td in viewTodo.children) {
                        Log.d("스냅맵j",td.value.toString())
                        val map = td.value as Map<String, Any?>
                        val uid = map.get("uid") as String? ?: ""
                        val title = map.get("title") as String? ?: ""
                        val dateStr = map.get("date") as String? ?: ""
                        val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
                        val memo = map.get("memo") as String? ?: ""
                        val key = map.get("key") as String? ?: ""
                        val location = map.get("location") as String ?: ""
                        val temp2 = Todo(uid,title,date,memo,key,location)
                        Log.d("스냅맵todo",temp2.toString())
                        todolist.add(temp2)
                    }

                    //Todo Friend
                    //Friend
                    val friendSnap = snapshot.child("Users").child(phone).child("MyFriendList")
                    for(fd in friendSnap.children) {
                        Log.d("친구스냅샷", fd.value.toString())
                        val fmap = fd.value as Map<String, Any?>
                        val fid = fmap.get("fid") as String? ?: ""
                        val friendTodo = snapshot.child("User-todolists").child(fid).child(temp.toString())
                        for(td in friendTodo.children) {
                            val map = td.value as Map<String, Any?>
                            val uid = map.get("uid") as String? ?: ""
                            val title = map.get("title") as String? ?: ""
                            val dateStr = map.get("date") as String? ?: ""
                            val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
                            val memo = map.get("memo") as String? ?: ""
                            val key = map.get("key") as String? ?: ""
                            val location = map.get("location") as String ?: ""
                            val temp2 = Todo(uid,title,date,memo,key,location)
                            todolist.add(temp2)
                        }
                    }


                    //Date - Me, Friend add
                    dateTodo.add(ViewCalendar(temp,todolist))
                }


                //CalendarList post
                Log.d("최종스냅todo",dateTodo.toString())
                view.postValue(dateTodo)
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
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE) //LocalDate

                val viewTodo = snapshot.child("User-todolists").child(phone).child(date.toString())
                //일정리스트
                val todolist = ArrayList<Todo>()

                //Me
                for(td in viewTodo.children) {
                    Log.d("스냅샷i",td.value.toString())
                    val map = td.value as Map<String, Any?>
                    val uid = map.get("uid") as String? ?: ""
                    val title = map.get("title") as String? ?: ""
                    val dateStr = map.get("date") as String? ?: ""
                    val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
                    val memo = map.get("memo") as String? ?: ""
                    val key = map.get("key") as String? ?: ""
                    val location = map.get("location") as String ?: ""
                    val temp = Todo(uid,title,date,memo,key,location)
                    Log.d("스냅샷todo",temp.toString())
                    todolist.add(temp)
                }

                //Todo Friend
                //Friend
                val friendSnap = snapshot.child("Users").child(phone).child("MyFriendList")
                for(fd in friendSnap.children) {
                    Log.d("친구스냅샷", fd.value.toString())
                    val fmap = fd.value as Map<String, Any?>
                    val fid = fmap.get("fid") as String? ?: ""
                    val friendTodo = snapshot.child("User-todolists").child(fid).child(date.toString())
                    for(td in friendTodo.children) {
                        val map = td.value as Map<String, Any?>
                        val uid = map.get("uid") as String? ?: ""
                        val title = map.get("title") as String? ?: ""
                        val dateStr = map.get("date") as String? ?: ""
                        val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
                        val memo = map.get("memo") as String? ?: ""
                        val key = map.get("key") as String? ?: ""
                        val location = map.get("location") as String ?: ""
                        val temp = Todo(uid,title,date,memo,key,location)
                        todolist.add(temp)
                    }
                }



                //Me, Friend post
                Log.d("최종스냅todo",todolist.toString())
                view.postValue(todolist)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    //새로운 일정 업데이트
    //newTodo(userId, title, Date, memo)
    fun postTodo(newTodo: Todo) {
        //yymd -> "20xx-xx-xx"
        //val yymd = newTodo.date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
        val yymd = newTodo.date?.toString()


        //1. key 생성 2. 객체 매핑 3. 해당경로/key 일정 업데이트
        var key: String? = ""

        //일정추가: 고유의 키 값 생성 후 push ex)-NHeffgDmkuygkDe4Mkb
        if(newTodo.key == "") {
            key = userRef.child("MyTodoList/$yymd").push().key
            if (key == null) {
                Log.w("키 널 로그", "Couldn't get push key for Todo")
                return
            }
        }
        //일정편집: 키 값 그대로 덮어쓰기
        else
            key = newTodo.key

        val newTodoKey = Todo(newTodo.uid,newTodo.title,newTodo.date,newTodo.memo, key)
        Log.d("키추가",newTodoKey.toString())
        val postValues = newTodoKey.toMap()


        //경로 설정
        val childUpdates = hashMapOf<String, Any>(
            "/Users/$phone/MyTodoList/$yymd/$key" to postValues,
            "/User-todolists/$phone/$yymd/$key" to postValues
        )
        dataRef.updateChildren(childUpdates)
    }

    //일정 한 개 읽기
    fun observeTodo(view: MutableLiveData<Todo>) {
        dataRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val viewDate = snapshot.child("Users").child(phone).child("viewDate")   //Users/phone/viewDate
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE) //LocalDate

                //일정 포인터
                val viewTodo = snapshot.child("Users").child(phone).child("viewTodo")
                val key = viewTodo.value.toString()

                //일정경로
                val todoSnap = snapshot.child("User-todolists").child(phone).child(date.toString()).child(key)
                Log.d("일정한개",todoSnap.value.toString())
                if(todoSnap.value != null) {
                    val map = todoSnap.value as Map<String, Any?>
                    val todouid = map.get("uid") as String? ?: ""
                    val todotitle = map.get("title") as String? ?: ""
                    val dateStr = map.get("date") as String? ?: ""
                    val tododate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
                    val todomemo = map.get("memo") as String? ?: ""
                    val todokey = map.get("key") as String? ?: ""
                    val todolocation = map.get("location") as String ?: ""
                    val temp = Todo(todouid, todotitle, tododate, todomemo, todokey,todolocation)
                    view.postValue(temp)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    //현재 유저가 선택한 일정 포인터 -> 일정 키 값
    fun postViewTodo(newTodo: Todo) {
        userRef.child("viewTodo").setValue(newTodo.key.toString())
    }

    //Todo 친구관련

    //유저의 친구목록 가져오기
    fun observeFriendlist(view: MutableLiveData<ArrayList<Friend>>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val friendSnap = snapshot.child("MyFriendList")

                val friendlist = ArrayList<Friend>()

                for(fs in friendSnap.children) {
                    Log.d("친구스냅샷",fs.value.toString())
                    val map = fs.value as Map<String, Any?>
                    val uid = map.get("uid") as String? ?: ""
                    val fid = map.get("fid") as String? ?: ""
                    val temp = Friend(uid,fid)
                    Log.d("친구스냅샷",temp.toString())
                    friendlist.add(temp)
                }

                Log.d("친구최종스냅",friendlist.toString())
                view.postValue(friendlist)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    //친구추가
    //newFriend(UserID, FriendID), Key는 FriendID
    fun postFriend(friendID: String) {

        //스왑
        val uidFriend = Friend(phone, friendID)
        val fidFriend = Friend(friendID, phone)
        val postUid = uidFriend.toMap()
        val postFid = fidFriend.toMap()

        //경로 설정
        val childUpdates = hashMapOf<String, Any>(
            "/Users/$phone/MyFriendList/$friendID" to postUid,
            "/Users/$friendID/MyFriendList/$phone" to postFid
        )

        dataRef.updateChildren(childUpdates)
    }

}