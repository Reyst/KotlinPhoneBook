package gsi.reyst.pb.adapters

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import gsi.reyst.pb.R
import gsi.reyst.pb.domain.Phone

class PhoneAdapter(private var model: MutableList<Phone>, val phoneEditor: PhoneAdapter.PhoneEditor) :
        RecyclerView.Adapter<PhoneAdapter.PhoneHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PhoneHolder(v)
    }

    override fun onBindViewHolder(holder: PhoneHolder, position: Int) {
        holder.text.text = model[position].phoneNo
    }

    override fun getItemCount(): Int {
        return model.size
    }

    inner class PhoneHolder(itemView: View) : RecyclerView.ViewHolder(itemView), PopupMenu.OnMenuItemClickListener {

        var text: TextView = itemView.findViewById(R.id.tv_text) as TextView
        val iMenu = itemView.findViewById(R.id.btn_menu) as ImageView

        init {
            iMenu.setOnClickListener {
                val popup = PopupMenu(it.context, it)
                popup.setOnMenuItemClickListener(this@PhoneHolder)
                popup.inflate(R.menu.actions)
                popup.show()
            }
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            val pos = adapterPosition
            when (item.itemId) {
                R.id.menu_edit -> phoneEditor.editPhone(model[pos], pos)
                R.id.menu_del -> {
                    model.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
            return true
        }
    }

    interface PhoneEditor {
        fun editPhone(phone: Phone, index: Int)
    }
}
