package com.anushka.roomdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anushka.roomdemo.db.Subscriber
import com.anushka.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository ):ViewModel() {

    val subscribers = repository.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateText = MutableLiveData<String>()
    val clearAllorDeleteText = MutableLiveData<String>()

    init {
        saveOrUpdateText.value = "Save"
        clearAllorDeleteText.value = "Clear ALL"
    }

    fun saveOrUpdate(){
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0,name, email))
        inputName.value = ""
        inputEmail.value = ""
    }

    fun clearOrDelete(){
        deleteAll()
    }

    fun insert(subscriber: Subscriber) =
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(subscriber)
        }

    fun update(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(subscriber)
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(subscriber)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) { repository.deleteAll() }


}