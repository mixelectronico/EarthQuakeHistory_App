package com.example.eartquakehistory.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.eartquakehistory.R
import com.example.eartquakehistory.model.EarthQuake
import kotlinx.android.synthetic.main.event_card.view.*

class EventAdapter(var listOfEvents : List<EarthQuake>): RecyclerView.Adapter<EventAdapter.EventViewHolder>(){

    fun updateData(newListOfEvents : List<EarthQuake>){
        listOfEvents = newListOfEvents
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_card,parent,false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventAdapter.EventViewHolder, position: Int) {
        val selectedEvent = listOfEvents[position]
            val selectedDate = selectedEvent.realDate
        holder.referenceTV.text = selectedEvent.RefGeografica
        holder.magnitudeTV.text = selectedEvent.Magnitud
        holder.latitudeTV.text = "Latitud: "+selectedEvent.Latitud
        holder.longitudeTV.text = "Longitud: "+selectedEvent.Longitud
        holder.deepnesTV.text = "Profundidad: "+selectedEvent.Profundidad
        holder.dateTV.text = "${selectedDate.dayOfWeek} ${selectedDate.dayOfMonth} de " +
                "${selectedDate.month} a las ${selectedDate.hour}:${selectedDate.minute}"
        holder.itemView.setOnClickListener {
            showLocation(selectedEvent, holder.itemView.context)
        }
    }

    override fun getItemCount(): Int {
        return listOfEvents.size
    }

    class EventViewHolder(itemview : View): RecyclerView.ViewHolder(itemview){
        var referenceTV = itemview.card_reference
        var magnitudeTV = itemview.card_magnitud
        var latitudeTV = itemview.card_latitud
        var longitudeTV = itemview.card_longitud
        var deepnesTV = itemview.card_profundidad
        var dateTV = itemview.card_fecha
    }

    fun showLocation(event: EarthQuake, context: Context){
        //Creates an Intent that will load a map of the point you pass
        val gmmIntentUri = Uri.parse("geo:${event.Latitud},${event.Longitud}?z=9")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(context,mapIntent,null)
    }
}
