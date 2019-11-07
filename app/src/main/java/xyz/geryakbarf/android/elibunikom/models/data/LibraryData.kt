package xyz.geryakbarf.android.elibunikom.models.data

import xyz.geryakbarf.android.elibunikom.R
import xyz.geryakbarf.android.elibunikom.models.LibraryModels

object LibraryData {
    private val libraryImage = intArrayOf(
        R.drawable.ilkom,
        R.drawable.science,
        R.drawable.journal,
        R.drawable.math,
        R.drawable.programming
    )

    private val libraryTitle = arrayOf(
        "Computer",
        "Science",
        "Journal",
        "Math",
        "Program"
    )

    private val libraryId = intArrayOf(
        1,
        2,
        3,
        4,
        5
    )

    val listLibrary : ArrayList<LibraryModels>
    get() {
        val list = arrayListOf<LibraryModels>()
        for (position in libraryImage.indices){
            val libraryModels = LibraryModels()
            libraryModels.img= libraryImage[position]
            libraryModels.name= libraryTitle[position]
            libraryModels.id= libraryId[position]
            list.add(libraryModels)
        }
        return list
    }

}