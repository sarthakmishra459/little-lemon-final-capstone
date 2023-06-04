package com.example.littlelemoncoursera.viewmodels.cart

import androidx.lifecycle.ViewModel
import com.example.littlelemoncoursera.cartItems
import com.example.littlelemoncoursera.data.local.entity.LocalDishItem
import com.example.littlelemoncoursera.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel:ViewModel() {

    private val _uiState = MutableStateFlow(CartUIState())
    val uiState: StateFlow<CartUIState> = _uiState.asStateFlow()

    fun changeQTY(item: CartItem,isIncrease:Boolean,itemCount:Int ){
        var tempCount = itemCount
        if(isIncrease){
            tempCount += 1
            cartItems.find { it.localDishItem.id == item.localDishItem.id }!!.quantity += 1
        }else{
            tempCount -= 1
            cartItems.find { it.localDishItem.id == item.localDishItem.id }!!.quantity -= 1
        }
        val emptyText = if(cartItems.isEmpty()) "Cart is Empty" else ""
        _uiState.update {
            it.copy(selectedCartItems = cartItems, emptyText = emptyText, itemCount = tempCount)
        }
    }

    fun removeItemFromCart(item: CartItem,){
        cartItems.remove(item);
        val emptyText = if(cartItems.isEmpty()) "Cart is Empty" else ""
        _uiState.update {
            it.copy(selectedCartItems = cartItems,  emptyText = emptyText)
        }
    }

    fun convertDishItems(selectedDishItems: MutableList<CartItem>): List<LocalDishItem> {
        val tempDishItems: MutableList<LocalDishItem> = mutableListOf()
        for (element in selectedDishItems){
            for (count in 1..element.quantity){
                tempDishItems.add(element.localDishItem)
            }
        }
        return tempDishItems.toList()
    }

    fun getTotal(selectedDishItems: MutableList<CartItem>):Int{
        var total = 0;
        for (element in selectedDishItems){
            total += element.quantity * element.localDishItem.price.toInt()
        }
        return total
    }
}