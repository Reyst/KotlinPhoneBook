package gsi.reyst.pb.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import gsi.reyst.pb.R
import gsi.reyst.pb.domain.Person
import gsi.reyst.pb.mvp.view.OnChangePersonListener


class PersonAdapter(val data: MutableList<Person>, val personEditor: PersonAdapter.PersonEditor) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnChangePersonListener {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        return PersonViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val person = data[position]
        with(holder as PersonViewHolder) {
            text.text = person.name + if (TextUtils.isEmpty(person.surname)) "" else ", " + person.surname
        }
    }

    override fun onPersonChanged(person: Person, index: Int) {
        data[index] = person
        notifyItemChanged(index)
    }

    override fun onPersonAdded(person: Person) {
        data.add(person)
        notifyDataSetChanged()
    }

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), PopupMenu.OnMenuItemClickListener {

        val text: TextView
        val iMenu: ImageView

        init {
            text = itemView.findViewById(R.id.tv_text) as TextView
            iMenu = itemView.findViewById(R.id.btn_menu) as ImageView

            iMenu.setOnClickListener({
                val popup = PopupMenu(it.context, it!!)
                popup.setOnMenuItemClickListener(this@PersonViewHolder)
                popup.inflate(R.menu.actions)
                popup.show()
            })
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            val pos = adapterPosition

            when (item.itemId) {
                R.id.menu_edit -> personEditor.editPerson(data[pos], pos)
                R.id.menu_del -> {
                    data.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
            return true
        }
    }

    interface PersonEditor {
        fun editPerson(person: Person, index: Int)
    }

}