package com.example.gptalk.utils


import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveEvent<T>() : MutableLiveData<T>() {

    //동시성을 보장하는 AtomicBoolean.
    private val mPending = AtomicBoolean(false)

    /**
     * View(Activity or Fragment 등 LifeCycleOwner)가 활성화 상태가 되거나
     * setValue로 값이 바뀌었을 때 호출되는 observe 함수.
     *
     * 아래의 setValue를 통해서만 pending이 true로 바뀌기 때문에,
     * 구성에 대한 변화가 일어나도 pending은 false이기 때문에 observe가
     * 데이터를 전달하지 않는다!
     */
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            println("Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    /**
     * setValue : UI Thread, 즉 Main Thread에서 실행
     *
     * LiveData로써 들고있는 데이터의 값을 변경하는 함수.
     * 여기서는 pending(AtomicBoolean)의 변수는 true로 바꾸어
     * observe내의 if문을 처리할 수 있도록 하였음.
     */
    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    // postValue : Background Thread에서 처리 (기존과 같음)
    override fun postValue(value: T) {
        super.postValue(value)
    }

    //T가 void일 경우에 활용
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private val TAG = "SingleLiveEvent"
    }
}
