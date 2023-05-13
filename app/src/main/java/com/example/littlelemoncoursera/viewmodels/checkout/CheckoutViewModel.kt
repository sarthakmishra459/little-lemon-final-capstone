package com.example.littlelemoncoursera.viewmodels.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.littlelemoncoursera.data.AddressRepository
import com.example.littlelemoncoursera.data.local.entity.AddressInformation
import com.example.littlelemoncoursera.data.local.entity.LocalDishItem
import com.example.littlelemoncoursera.localDishDatabase
import com.example.littlelemoncoursera.model.PaymentMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class CheckoutViewModel():ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutUIState())
    val uiState: StateFlow<CheckoutUIState> = _uiState.asStateFlow()

    fun increaseQty(prevQty:Int,originalPrice:Int){
        val newQty=prevQty+1
        _uiState.update {
            it.copy(
                selectedQty = newQty, totalPrice =originalPrice*newQty
            )
        }
    }

    fun decreaseQty(prevQty:Int,originalPrice:Int){
        val newQty=if(prevQty>1) prevQty-1 else prevQty
        _uiState.update {
            it.copy(
                selectedQty = newQty, totalPrice =originalPrice*newQty
            )
        }
    }

    fun setItemAndPrice(newItemsList:List<LocalDishItem>, newPrice:Int){
        _uiState.update {
            it.copy(selectedItems = newItemsList, totalPrice = newPrice)
        }
    }

    fun getAllAddresses():LiveData<List<AddressInformation>>{
        return localDishDatabase.localDishDao().getAllAddressInformation();
    }

    suspend fun addANewAddress(addressInformation: AddressInformation) = withContext(Dispatchers.IO){
        localDishDatabase.localDishDao().addANewAddress(addressInformation);
        showOrHideAddForm(false)
    }

    fun showOrHideAddForm(showOrHide:Boolean){
        _uiState.update {
            it.copy(showAddForm = showOrHide)
        }
    }

    fun onAddressSelect(addressInformation: AddressInformation){
        _uiState.update {
            it.copy(selectedAddress = addressInformation)
        }
    }

    fun onPaymentSelect(paymentMethod: PaymentMethod){
        _uiState.update {
            it.copy(selectedPaymentMethod = paymentMethod)
        }
    }
}