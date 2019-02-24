package kov.ru.testmap.ui.view.fragment


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_cities.*
import kov.ru.testmap.App
import kov.ru.testmap.R
import kov.ru.testmap.model.City
import kov.ru.testmap.model.db.DBListenerDelete
import kov.ru.testmap.model.db.DBRepository
import kov.ru.testmap.ui.presenter.CitiesPresenter
import kov.ru.testmap.ui.view.adapter.Adapter
import kov.ru.testmap.view.Cities
import javax.inject.Inject



class CitiesFragment : Fragment(), Cities.View {

    @Inject
    lateinit var presenter: CitiesPresenter
    private lateinit var adapter: Adapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.getInstance().injector.injectCitiesPFragment(this)
        val view = inflater.inflate(R.layout.fragment_cities, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = Adapter()
        recyclerView.adapter = adapter
        presenter.attachView(this)

        swipeToDelete()


        return view
    }

    private fun swipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                DBRepository.getInstance().delete((viewHolder as Adapter.ViewHolder).city!!, object : DBListenerDelete {
                    override fun cityDeleted(city: City) {
                        //Фрагменты подписаны на репозиторий для своевременного обновления списков.
                        DBRepository.getInstance().getAll(null)
                    }

                })
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            val dialog = DialogFragment()
            val manager = activity.fragmentManager
            dialog.show(manager, null)
        }
    }

    override fun clearList() {
        //очищаем список и добавляем элементы из базы. Подумал, что так лучше для актуализации информации
        activity?.runOnUiThread {
            adapter.showElements(null)
        }
    }

    override fun showList(cities: ArrayList<City>) {
        activity?.runOnUiThread {
            adapter.showElements(cities)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}


