package com.example.myprojectteam7.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.myprojectteam7.*
import com.example.myprojectteam7.Todo
import com.example.myprojectteam7.ViewCalendar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
class CalendarsRepository(key: String?) {
    val database = Firebase.database

    //userRef -> /Users
    val userRef = database.getReference(key ?: "bug")
    var phone: String = ""



    //유저 정보 가져오기
    fun observeUser(view: MutableLiveData<User>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userSnap = snapshot.child(phone)
                val user = searchUser(userSnap.value as Map<String, Any?>)

                view.postValue(user)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    //새로운 유저 추가
    fun postNewUser(newUser: User) {
        val postValues = newUser.toMap()

        val childUpdates = hashMapOf<String, Any>("/$phone" to postValues)

        userRef.updateChildren(childUpdates)
    }


    //viewDate 쓰기 -> 유저의 Date 포인터
    fun postViewDate(newDate: LocalDate) {
        userRef.child(phone).child("viewDate").setValue(newDate.toString())
    }

    //달력 관련
    //Date-Todolist 객체
    //Date하나에-일정여러개 매핑 -> 리사이클러뷰
    fun observeCalendar(view: MutableLiveData<ArrayList<ViewCalendar>>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val viewDate = snapshot.child(phone).child("viewDate")   //Users/phone/viewDate
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE) //LocalDate
                val firstday = LocalDate.of(date.year,date.monthValue,1)
                val firstdayOfWeek = if(firstday.dayOfWeek.value!=7) firstday.dayOfWeek.value else 0
                val startdate = firstday.minusDays(firstdayOfWeek.toLong())


                val dateTodo = ArrayList<ViewCalendar>()
                //42칸 달력 표시
                for(dt in 0.. 41) {
                    val temp = startdate.plusDays(dt.toLong())   //변하는 Date


                    //todolist추가
                    //친구는 관계에서 번호를 가져온 후에 반복문 한번 더
                    val viewTodo = snapshot.child(phone).child("MyTodoList").child(temp.toString())
                    val todolist = ArrayList<Todo>()
                    //Me
                    for(td in viewTodo.children) {
                        todolist.add(searchTodo(td.value as Map<String, Any?>))
                    }

                    //Todo Friend
                    //Friend
                    val friendSnap = snapshot.child(phone).child("MyFriendList")
                    for(fd in friendSnap.children) {
                        val fmap = fd.value as Map<String, Any?>
                        val fid = fmap.get("fid") as String? ?: ""
                        val friendTodo = snapshot.child(fid).child("MyTodoList").child(temp.toString())
                        for(td in friendTodo.children) {
                            todolist.add(searchTodo(td.value as Map<String, Any?>))
                        }
                    }

                    //Date - Me, Friend add
                    dateTodo.add(ViewCalendar(temp,todolist))
                }


                //CalendarList post
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
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val viewDate = snapshot.child(phone).child("viewDate")   //Users/phone/viewDate
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE) //LocalDate

                val viewTodo = snapshot.child(phone).child("MyTodoList").child(date.toString())

                //일정리스트
                val todolist = ArrayList<Todo>()

                //Me
                for(td in viewTodo.children) {
                    todolist.add(searchTodo(td.value as Map<String, Any?>))
                }

                //Todo Friend
                //Friend
                val friendSnap = snapshot.child(phone).child("MyFriendList")
                for(fd in friendSnap.children) {
                    val fmap = fd.value as Map<String, Any?>
                    val fid = fmap.get("fid") as String? ?: ""
                    val friendTodo = snapshot.child(fid).child("MyTodoList").child(date.toString())
                    for(td in friendTodo.children) {
                        todolist.add(searchTodo(td.value as Map<String, Any?>))
                    }
                }

                //Me, Friend post
                view.postValue(todolist)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    //새로운 일정 업데이트
    //newTodo(userId, title, Date, memo)
    fun postTodo(newTodo: Todo) {
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

        val newTodoKey = Todo(newTodo.uid,newTodo.author,newTodo.title,newTodo.date,newTodo.memo, key)
        Log.d("키추가",newTodoKey.toString())
        val postValues = newTodoKey.toMap()


        //경로 설정
        val childUpdates = hashMapOf<String, Any>(
            "/$phone/MyTodoList/$yymd/$key" to postValues
        )
        userRef.updateChildren(childUpdates)
    }



    //일정 한 개 읽기
    fun observeTodo(view: MutableLiveData<Todo>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val viewDate = snapshot.child(phone).child("viewDate")   //Users/phone/viewDate
                val date = LocalDate.parse(viewDate.value.toString(), DateTimeFormatter.ISO_DATE) //LocalDate

                //일정 포인터
                val viewTodo = snapshot.child(phone).child("viewTodo")
                val key = viewTodo.value.toString()

                //일정경로
                val todoSnap = snapshot.child(phone).child("MyTodoList").child(date.toString()).child(key)

                if(todoSnap.value != null) {
                    view.postValue(searchTodo(todoSnap.value as Map<String, Any?>))
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    //현재 유저가 선택한 일정 포인터 -> 일정 키 값
    fun postViewTodo(newTodo: Todo) {
        userRef.child(phone).child("viewTodo").setValue(newTodo.key.toString())
    }


    //Todo 친구관련

    //유저의 친구목록 가져오기
    fun observeFriendlist(view: MutableLiveData<ArrayList<Friend>>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val friendSnap = snapshot.child(phone).child("MyFriendList")

                val friendlist = ArrayList<Friend>()

                for(fs in friendSnap.children) {
                    val map = fs.value as Map<String, Any?>
                    val uid = map.get("uid") as String? ?: ""
                    val fid = map.get("fid") as String? ?: ""
                    val temp = Friend(uid, fid)
                    friendlist.add(temp)
                }


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
            "/$phone/MyFriendList/$friendID" to postUid,
            "/$friendID/MyFriendList/$phone" to postFid
        )

        userRef.updateChildren(childUpdates)
    }

    //일정 정보 가져오기
    fun searchTodo(map: Map<String,Any?>): Todo {
        val todouid = map.get("uid") as String? ?: ""
        val todotitle = map.get("title") as String? ?: ""
        val todoname = map.get("author") as String? ?: ""
        val dateStr = map.get("date") as String? ?: ""
        val tododate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
        val todomemo = map.get("memo") as String? ?: ""
        val todokey = map.get("key") as String? ?: ""
        val todo = Todo(todouid, todoname, todotitle, tododate, todomemo, todokey)
        return todo
    }


    //유저 정보 가져오기
    fun searchUser(map: Map<String,Any?>): User {
        val name = map.get("username") as String? ?: ""
        val phone = map.get("usernumber") as String? ?: ""
        val pw = map.get("userpassword") as String? ?: ""
        val dateStr = map.get("viewDate") as String? ?: ""
        val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
        val todo = map.get("viewTodo") as String? ?: ""
        val user = User(name, phone ,pw, date, todo)
        return user
    }

}