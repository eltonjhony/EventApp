package com.eltonjhony.eventapp.presentation.features.listevents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.infrastructure.extensions.load
import com.eltonjhony.eventapp.infrastructure.extensions.pushAll
import com.eltonjhony.eventapp.infrastructure.extensions.roundBorders
import com.eltonjhony.eventapp.presentation.features.listevents.EventsAdapter.EventsViewHolder
import kotlinx.android.synthetic.main.event_item.view.*
import java.util.*

class EventsAdapter(
    private val events: MutableList<Event> = mutableListOf(),
    private val filteredEvents: MutableList<Event> = mutableListOf(),
    private val clickListener: (Event) -> Unit
) : RecyclerView.Adapter<EventsViewHolder>(), Filterable {
    var currentFilter = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        )
    }

    override fun getItemCount(): Int = filteredEvents.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = filteredEvents[position]
        holder.bind(event)
        holder.itemView.setOnClickListener { clickListener(event) }
    }

    override fun getFilter(): Filter = EventsFilter()

    fun setEvents(data: List<Event>?) {
        data?.let {
            events.pushAll(data)
            filteredEvents.pushAll(data)
            notifyDataSetChanged()
        }
    }

    fun updateEvents(data: List<Event>?) {
        data?.let {
            val positionStart = events.size + 1
            events.addAll(it)
            filteredEvents.addAll(it)
            notifyItemRangeInserted(positionStart, it.size)
        }
    }

    fun clear() {
        events.clear()
        filteredEvents.clear()
        notifyDataSetChanged()
    }

    fun didAddToWishList(event: Event) {
        filteredEvents.firstOrNull { it.id == event.id }?.run {
            isFavorite = true
            notifyDataSetChanged()
        }
    }

    fun didRemoveFromWishList(event: Event, shouldDelete: Boolean = false) {
        filteredEvents.firstOrNull { it.id == event.id }?.run {
            if (shouldDelete)
                filteredEvents.remove(this)
            else
                isFavorite = false
            notifyDataSetChanged()
        }
    }

    fun isEmpty(): Boolean = filteredEvents.isEmpty()

    inner class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(event: Event) {
            itemView.apply {
                eventImageView.roundBorders(10)
                eventImageView.load(event.getThumbImageUrl())

                weekDayView.setup(event.getDay(), event.getDayOfWeek())
                favoriteView.setup(event)

                eventNameTextView.text = event.name
                eventLocationTextView.text =
                    event.getCity() ?: itemView.context.getString(R.string.to_be_defined_label)
                monthView.month = event.getMonth()
                eventStartTextView.text =
                    event.getStartTime() ?: context.getString(R.string.to_be_defined_label)

            }
        }

    }

    inner class EventsFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            filteredEvents.pushAll(events)
            val query = constraint.toString()
            currentFilter = query
            if (query.isNotEmpty()) {
                filteredEvents.pushAll(events.filter {
                    it.name?.toLowerCase(Locale.ROOT)?.startsWith(query) ?: false
                })
            }
            val filterResults = FilterResults()
            filterResults.values = filteredEvents
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val filteredResults = (results?.values as List<Event>).toMutableList()
            filteredEvents.pushAll(filteredResults)
            notifyDataSetChanged()
        }
    }

}
