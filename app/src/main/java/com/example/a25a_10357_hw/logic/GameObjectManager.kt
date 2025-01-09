package com.example.a25a_10357_hw.logic

import android.content.Context
import android.view.View
import com.example.a25a_10357_hw.interfaces.GameObject
import androidx.appcompat.widget.AppCompatImageView


class GameObjectManager(
    private val context: Context,
    private val views: Array<Array<AppCompatImageView>>,
    private val gameManager: GameManager,
    private val hearts: Array<AppCompatImageView>,
    private val cowRow: Int
) {
    private val objects = mutableListOf<GameObjectInstance>()

    data class GameObjectInstance(
        val gameObject: GameObject,
        var row: Int,
        var column: Int
    )


    fun moveObjects(cowCurrentColumn: Int): Boolean {
        val newObjects = mutableListOf<GameObjectInstance>()
        var isGameOver = false

        for (obj in objects) {
            if (!(obj.row == cowRow && obj.column == cowCurrentColumn)) {
                views[obj.row][obj.column].visibility = View.INVISIBLE
            }

            val newRow = obj.row + 1

            if (newRow == cowRow && obj.column == cowCurrentColumn) {
                if (obj.gameObject.onCollision(context, gameManager, hearts)) {
                    isGameOver = true
                }
            } else if (newRow < 7) {
                try {
                    views[newRow][obj.column].apply {
                        setImageResource(obj.gameObject.imageResource)
                        visibility = View.VISIBLE
                    }
                    newObjects.add(GameObjectInstance(obj.gameObject, newRow, obj.column))
                } catch (e: Exception) {
                }
            }
        }

        objects.clear()
        objects.addAll(newObjects)
        return isGameOver
    }

    fun addNewObject(gameObject: GameObject) {
        val newColumn = (0..4).random()

        views[0][newColumn].apply {
            setImageResource(gameObject.imageResource)
            visibility = View.VISIBLE
        }

        objects.add(GameObjectInstance(gameObject, 0, newColumn))
    }

    fun checkCollisions(cowCurrentColumn: Int): Boolean {
        var isGameOver = false
        objects.forEach { obj ->
            if (obj.row == cowRow && obj.column == cowCurrentColumn) {
                if (obj.gameObject.onCollision(context, gameManager, hearts)) {
                    isGameOver = true
                }
            }
        }
        return isGameOver
    }



    fun clear() {
        objects.clear()
    }

    fun objectsCount() = objects.size
}