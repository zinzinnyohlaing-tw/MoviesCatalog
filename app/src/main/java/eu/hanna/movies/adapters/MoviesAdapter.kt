package eu.hanna.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.hanna.movies.databinding.MoviesItemBinding
import eu.hanna.movies.models.Result
import eu.hanna.movies.util.Constants.Companion.IMAGE_BASE

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    //private var IMAGE_BASE = "https://image.tmdb.org/t/p/w500"

    private var movieList = ArrayList<Result>()
    lateinit var onItemClick:((Result) -> Unit)

    class MoviesViewHolder(val itemBinding: MoviesItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object: DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
           return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
       return MoviesViewHolder(MoviesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]

        Glide.with(holder.itemView)
            .load(IMAGE_BASE + movie.poster_path)
            .into(holder.itemBinding.moviesImg)

        holder.itemBinding.movieText.text = movie.title

        holder.itemView.setOnClickListener {
            onItemClick.invoke(movie)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}