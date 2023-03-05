package com.example.plapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plapp.databinding.ProgrammingLanguageCardBinding

class RVProgrammingLanguageAdapter(private val onCardClickListener: OnProgrammingLanguageCardClicked) :
    RecyclerView.Adapter<RVProgrammingLanguageAdapter.ViewHolder>(),
    OnProgrammingLanguageCardClicked by onCardClickListener {

    class ViewHolder(binding: ProgrammingLanguageCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val cvProgrammingLanguageCard = binding.cvProgrammingLanguageCard
        val tvProgrammingLanguageName = binding.tvProgrammingLanguageName
        val ivProgrammingLanguageLogo = binding.ivProgrammingLanguageLogo
        val tvProgrammingLanguageDetail = binding.tvProgrammingLanguageDetail
    }

    private val diffCallback = object : DiffUtil.ItemCallback<ProgrammingLanguage>() {
        override fun areItemsTheSame(
            oldItem: ProgrammingLanguage,
            newItem: ProgrammingLanguage
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ProgrammingLanguage,
            newItem: ProgrammingLanguage
        ): Boolean = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listProgrammingLanguage: List<ProgrammingLanguage>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProgrammingLanguageCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val programmingLanguage = listProgrammingLanguage[position]
        holder.apply {
            cvProgrammingLanguageCard.setOnClickListener { onCardClicked(it, programmingLanguage) }
            tvProgrammingLanguageName.text = programmingLanguage.name
            Glide
                .with(ivProgrammingLanguageLogo.context)
                .load(programmingLanguage.logo)
                .centerInside()
                .into(ivProgrammingLanguageLogo)
            tvProgrammingLanguageDetail.text = programmingLanguage.shortDescription
        }
    }

    override fun getItemCount(): Int = listProgrammingLanguage.size
}