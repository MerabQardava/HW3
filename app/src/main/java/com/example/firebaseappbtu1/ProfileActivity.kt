package com.example.firebaseappbtu1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var linkEditText : EditText
    private lateinit var usernameEditText : EditText
    private lateinit var editProfileButton : Button
    private lateinit var userNameTextView : TextView
    private lateinit var imageView : ImageView
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance().getReference("User")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
        listeners()
    }

    private fun listeners() {
        editProfileButton.setOnClickListener {
            val link = linkEditText.text.toString()
            val username = usernameEditText.text.toString()
            val userInfo = User(auth.currentUser?.email,
                auth.currentUser!!.uid,
                link, username)
            db.child(auth.currentUser!!.uid).setValue(userInfo)
        }
    }

    private fun init(){
        linkEditText = findViewById(R.id.linkEditText)
        usernameEditText = findViewById(R.id.usernameEditText)
        editProfileButton = findViewById(R.id.editProfileButton)
        userNameTextView = findViewById(R.id.userNameTextView)
        imageView = findViewById(R.id.imageView)

        userNameTextView.text = auth.uid

        db.child(auth.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(User::class.java) ?: return
                userNameTextView.text = userInfo.userName
                val link = userInfo.link
                Glide.with(this@ProfileActivity).load(link).into(imageView)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        }

        )

    }

}