package com.internshalaandroid.interest.adapters
import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.PermissionChecker.checkPermission
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.internshalaandroid.interest.R
import com.internshalaandroid.interest.activity.MainActivity1
import com.internshalaandroid.interest.activity.MyDbHelper
import com.internshalaandroid.interest.fragments.clients
import com.internshalaandroid.interest.fragments.home
import com.internshalaandroid.interest.model.Cdata
import kotlinx.android.synthetic.main.updateamount.view.*
import java.security.AccessController.checkPermission
import java.text.DecimalFormat


class client_adapter(val context: Context, val itemList:MutableList<Cdata>) : RecyclerView.Adapter<client_adapter.clientViewHolder>() {
    internal var dbhelper=MyDbHelper(context)
    internal var client=clients()
    val mctx=context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): clientViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycle_client_singlerow,parent,false)
        return clientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: clientViewHolder, position: Int) {
      val cdata= itemList[position]
        holder.cname.text=cdata.Client_name
        holder.cph.text=cdata.CLient_ph
        holder.caadhar.text=cdata.Client_Aadhar
        holder.caddress.text=cdata.Client_address
        holder.camount.text=cdata.Client_amount
        holder.cinterest.text=cdata.Client_interest
        holder.cdataTime.text=cdata.Client_dataTime
        holder.cgap.text=cdata.Client_gap
        holder.intial.text=cdata.Client_intial
        holder.deletButton.setOnClickListener{

            val builder=AlertDialog.Builder(context)
            builder.setTitle("Delete")
            //set message for alert dialog
            builder.setMessage("Do you want to delete permanently")
            builder.setIcon(R.drawable.ic_dialag_alert)
            builder.setPositiveButton("YES")
            {
                    dialog, which ->


                var helper = MyDbHelper(context)
                var db = helper.writableDatabase
                val ph=cdata.CLient_ph.toString()
                try {
                    dbhelper.deleteData(cdata.CLient_ph.toString())

                        itemList.removeAt(position)
                       notifyItemRemoved(position)
                       notifyItemRangeChanged(position,itemList.size)
                    Toast.makeText(context,"Deleted client detail",Toast.LENGTH_SHORT).show()
                }
                catch (e:Exception)
                {
                    Toast.makeText(context,"Error deletion",Toast.LENGTH_SHORT).show()
                }

            }
            builder.setNegativeButton("NO")
            {
                    dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }
        holder.notifybutton.setOnClickListener {
            val builder1=AlertDialog.Builder(context)
            builder1.setTitle("Send Message")
            //set message for alert dialog
            builder1.setMessage("Do you want to send message")
            builder1.setIcon(R.drawable.ic_baseline_send_24)
            val phone=cdata.CLient_ph.toString()
            val amount:Long=cdata.Client_amount.toLong()
            val inter:Int=cdata.Client_interest.toInt()
            val interest:Long= (amount*inter)/100
            val name:String=cdata.Client_name.toString()
            val  bmessage1:String=" Do you want to message "+name+" "
            builder1.setMessage(bmessage1)
            val text:String= "Hello "+name+" This is a reminder that you have to send me the interest amount for the debt you took from me, interest amount is " +interest+ " Thank you "
            builder1.setPositiveButton("YES")
            {
                    dialog, which ->
                try {
                    myMessage(phone,text)
                }
                catch (e: Exception)
                {
                    Toast.makeText(context,"Unable to send sms",Toast.LENGTH_SHORT).show()
                }
}
            builder1.setNegativeButton("NO")
            {
                    dialog, which ->
                dialog.dismiss()
            }
            builder1.show()
        }
       holder.callButton.setOnClickListener {
           val phonecall=cdata.CLient_ph.toString()
           val nameclient:String=cdata.Client_name.toString()
           val  bmessage:String=" Do you want to call "+nameclient+" "
           val builder2=AlertDialog.Builder(context)
             builder2.setTitle("Make a Call ")
             //set message for alert dialog
             builder2.setMessage(bmessage)
             builder2.setIcon(R.drawable.ic_call)
             builder2.setPositiveButton("YES")
             {
                     dialog, which ->
                 try {
                        mycall(phonecall)
                 }
                 catch (e: Exception)
                 {
                     Toast.makeText(context,"Unable to call",Toast.LENGTH_SHORT).show()
                 }
             }
             builder2.setNegativeButton("NO")
             {
                     dialog, which ->
                 dialog.dismiss()
             }
             builder2.show()
        }
        holder.update.setOnClickListener {
            val inflater=LayoutInflater.from(mctx)
            val view1=inflater.inflate(R.layout.updateamount,null)

            val modifyamount:EditText=view1.findViewById(R.id.edtupamount)
            val  bmessage:String="  update "+modifyamount+" intial amount "
            val phone=cdata.CLient_ph.toString()
            val builder3=AlertDialog.Builder(context)
             builder3.setTitle("Update")

            builder3.setView(view1)
             builder3.setIcon(R.drawable.ic_update)
             builder3.setPositiveButton("YES")
             {
                     dialog, which ->

                 try {
                     val isUpdate:Boolean=dbhelper.update(phone,view1.edtupamount.text.toString())
                     if(isUpdate==true)
                     {
                         cdata.Client_amount=view1.edtupamount.text.toString()
                         notifyDataSetChanged()
                         Toast.makeText(context,"Update successful",Toast.LENGTH_SHORT).show()
                     }
                     else{
                         Toast.makeText(context,"Update failed",Toast.LENGTH_SHORT).show()
                     }
                 }
                catch (e: Exception)
                {
                    Toast.makeText(context, "Unable to update try later", Toast.LENGTH_SHORT).show()
                }
             }
             builder3.setNegativeButton("NO")
             {
                     dialog, which ->
                 dialog.dismiss()
             }
             builder3.show()
         }

    }
    class clientViewHolder(view: View):RecyclerView.ViewHolder(view){
        val cname: TextView=view.findViewById(R.id.txtname)
        val cph: TextView=view.findViewById(R.id.phnum)
        val caadhar:TextView=view.findViewById(R.id.aadhar)
        val caddress:TextView=view.findViewById(R.id.address)
        var camount:TextView=view.findViewById(R.id.amount)
        val cinterest:TextView=view.findViewById(R.id.interest)
        val deletButton:ImageButton=view.findViewById(R.id.deleteButton)
        val notifybutton:ImageButton=view.findViewById(R.id.notifyButton)
        val callButton:ImageButton=view.findViewById(R.id.callbutton)
        val cdataTime:TextView=view.findViewById(R.id.dateTime)
        val cgap:TextView=view.findViewById(R.id.cgap)
        val intial:TextView=view.findViewById(R.id.intial1)
        val update:ImageButton=view.findViewById(R.id.update)
    }
    fun myMessage(myNumber:String, myMsg:String) {
        if (myNumber == "" || myMsg == "") {
            Toast.makeText(context, "Field cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            if (TextUtils.isDigitsOnly(myNumber)) {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(myNumber, null, myMsg, null, null)
                Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please enter the correct number", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun mycall(myNumber:String) {
        if (myNumber == "") {
            Toast.makeText(context, "No Number", Toast.LENGTH_SHORT).show()
        } else {
            if (TextUtils.isDigitsOnly(myNumber)) {
                val intent = Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:$myNumber"))
               context.startActivity(intent)
                Toast.makeText(context, "Call Sent", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context, "Please enter the correct number", Toast.LENGTH_SHORT).show()
            }
        }
    }


}