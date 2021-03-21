package com.your.time.app

import com.your.time.app.domain.model.actions.ActionFactoryImpl
import com.your.time.app.domain.model.actions.ActionModel
import org.junit.Assert.assertTrue
import org.mockito.Mockito
import kotlin.reflect.KClass

fun <T: Exception> Any.assertException(action: () -> Unit, type: KClass<T>, message: String = "There should have been an exception"){
    assertTrue(message, isException(action, type) != null)
}

fun Any.assertNotException(action: () -> Unit, message: String = "There should have been no exception"){
    val exception = isException(action)
    exception?.also { it.printStackTrace() }
    assertTrue(message, exception == null)
}

private fun <T: Exception> isException(action: () -> Unit, type: KClass<T>): Exception? {
    return try{
        action.invoke()
        null
    }catch (e: Exception){
        e
    }
}

private fun isException(action: () -> Unit): Exception?{
    return try{
        action.invoke()
        null
    }catch (e: Exception){
        e
    }
}



fun <T> Any.safeAny(): T {
    Mockito.any<T>()
    return uninitialized()
}
private fun <T> uninitialized(): T = null as T

fun <T : Any> Any.safeEq(value: T): T = Mockito.eq(value) ?: value



val veryHarmfullyAction = ActionModel("", usefulness = ActionModel.Usefulness.VERY_HARMFULLY, color = 1)
val harmfullyAction = ActionModel("", usefulness = ActionModel.Usefulness.HARMFULLY, color = 1)
val neutrallyAction = ActionModel("", usefulness = ActionModel.Usefulness.NEUTRALLY, color = 1)
val usefullyAction = ActionModel("", usefulness = ActionModel.Usefulness.USEFULLY, color = 1)
val veryUsefullyAction = ActionModel("", usefulness = ActionModel.Usefulness.VERY_USEFULLY, color = 1)