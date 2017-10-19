package pl.treksoft.kvision.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair

abstract class AbstractTextInput(placeholder: String? = null,
                override var value: String? = null, name: String? = null, maxlength: Int? = null,
                disabled: Boolean = false, id: String? = null,
                classes: Set<String> = setOf()) : Widget(classes + "form-control"), StringFormField {
    init {
        this.id = id
    }

    @Suppress("LeakingThis")
    var startValue: String? = value
        set(value) {
            field = value
            this.value = value
            refresh()
        }
    var placeholder: String? = placeholder
        set(value) {
            field = value
            refresh()
        }
    var name: String? = name
        set(value) {
            field = value
            refresh()
        }
    var maxlength: Int? = maxlength
        set(value) {
            field = value
            refresh()
        }
    override var disabled: Boolean = disabled
        set(value) {
            field = value
            refresh()
        }
    var autofocus: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    var readonly: Boolean? = null
        set(value) {
            field = value
            refresh()
        }
    override var size: INPUTSIZE? = null
        set(value) {
            field = value
            refresh()
        }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        placeholder?.let {
            sn.add("placeholder" to it)
        }
        name?.let {
            sn.add("name" to it)
        }
        autofocus?.let {
            if (it) {
                sn.add("autofocus" to "autofocus")
            }
        }
        maxlength?.let {
            sn.add("maxlength" to ("" + it))
        }
        readonly?.let {
            if (it) {
                sn.add("readonly" to "readonly")
            }
        }
        if (disabled) {
            sn.add("disabled" to "true")
        }
        return sn
    }

    override fun afterInsert(node: VNode) {
        this.getElementJQuery()?.on("input", { _, _ ->
            val v = getElementJQuery()?.`val`() as String?
            if (v != null && v.isNotEmpty()) {
                value = v
            } else {
                value = null
            }
        })
    }
}