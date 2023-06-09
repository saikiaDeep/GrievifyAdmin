package com.example.grievifyadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
//
//        //setting adapter to recyclerview
//        chatAdapter = ChatAdapter(this, messageList)
//        binding.chatView.adapter = chatAdapter
//
//        //onclick listener to update the list and call dialogflow
//        binding.btnSend.setOnClickListener {
//            val message: String = binding.editMessage.text.toString()
//            if (message.isNotEmpty()) {
//                addMessageToList(message, false)
//                sendMessageToBot(message)
//            } else {
//                Toast.makeText(this, "Please enter text!", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        //initialize bot config
//        setUpBot()
//    }
//
//    private fun addMessageToList(message: String, isReceived: Boolean) {
//        messageList.add(Message(message, isReceived))
//        binding.editMessage.setText("")
//        chatAdapter.notifyDataSetChanged()
//        binding.chatView.layoutManager?.scrollToPosition(messageList.size - 1)
//    }
//
//    private fun setUpBot() {
//        try {
//            //val stream = this.resources.openRawResource(R.raw.credential)
////            val credentials: GoogleCredentials = GoogleCredentials.fromStream(stream)
////                .createScoped("https://www.googleapis.com/auth/cloud-platform")
////            val projectId: String = (credentials as ServiceAccountCredentials).projectId
////            val settingsBuilder: SessionsSettings.Builder = SessionsSettings.newBuilder()
////            val sessionsSettings: SessionsSettings = settingsBuilder.setCredentialsProvider(
////                FixedCredentialsProvider.create(credentials)
////            ).build()
////            sessionsClient = SessionsClient.create(sessionsSettings)
////            sessionName = SessionName.of(projectId, uuid)
////            Log.d(TAG, "projectId : $projectId")
//        } catch (e: Exception) {
//            Log.d(TAG, "setUpBot: " + e.message)
//        }
//    }
//
//    private fun sendMessageToBot(message: String) {
//        val input = QueryInput.newBuilder()
//            .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build()
//        GlobalScope.launch {
//            sendMessageInBg(input)
//        }
//    }
//
//    private suspend fun sendMessageInBg(
//        queryInput: QueryInput
//    ) {
//        withContext(Default) {
//            try {
//                val detectIntentRequest = DetectIntentRequest.newBuilder()
//                    .setSession(sessionName.toString())
//                    .setQueryInput(queryInput)
//                    .build()
//                val result = sessionsClient?.detectIntent(detectIntentRequest)
//                if (result != null) {
//                    runOnUiThread {
//                        updateUI(result)
//                    }
//                }
//            } catch (e: java.lang.Exception) {
//                Log.d(TAG, "doInBackground: " + e.message)
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun updateUI(response: DetectIntentResponse) {
//        val botReply: String = response.queryResult.fulfillmentText
//        if (botReply.isNotEmpty()) {
//            addMessageToList(botReply, true)
//        } else {
//            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
//        }
//    }
    }
}