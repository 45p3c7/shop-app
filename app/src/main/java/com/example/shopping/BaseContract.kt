package com.example.shopping

interface BaseView {

}

interface BasePresenter<T : BaseView> {

    var view : T?

    fun onAttach(view : T) {
        this.view = view
    }

    fun onDettach() {
        this.view = null
    }
}