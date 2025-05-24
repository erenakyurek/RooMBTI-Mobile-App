package com.example.roombti

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roombti.UserAdapter.UserViewHolder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<MessageData>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEVIED = 1;
    val ITEM_SENT = 2;

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val receivedMessage = itemView.findViewById<TextView>(R.id.txt_received_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1){

            val view: View = LayoutInflater.from(context).inflate(R.layout.received, parent, false)
            return ReceivedViewHolder(view)

        }else{

            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)

        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderID)){
            return ITEM_SENT
        }else{
            return ITEM_RECEVIED
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass == SentViewHolder::class.java){

            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message


        }else{

            val viewHolder = holder as ReceivedViewHolder
            holder.receivedMessage.text = currentMessage.message


        }
    }
}