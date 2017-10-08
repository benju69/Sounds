package me.benju.sounds.ui

/**
 * Created by benju on 06/10/2017.
 */
interface BaseView<T> {
    fun setPresenter(presenter: T)
}