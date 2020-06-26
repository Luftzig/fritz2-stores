import dev.fritz2.binding.KotlinXJsonSerializer
import dev.fritz2.binding.LocalStorageEntityStore
import dev.fritz2.binding.const
import dev.fritz2.binding.handledBy
import dev.fritz2.dom.html.render
import dev.fritz2.dom.mount
import dev.fritz2.dom.values
import dev.fritz2.lenses.buildLens
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.serialization.Serializable

@Serializable
data class Model(val text: String)

@ExperimentalCoroutinesApi
@FlowPreview
fun main() {

    val store = object : LocalStorageEntityStore<Model, String>(
        Model(""),
        { _ -> "" },
        "dev.fritz2.sample",
        KotlinXJsonSerializer(Model.serializer())
    ) {
        val save = handle {
            it.apply(::saveOrUpdate)
        }

        val load = handle {
            load("") ?: Model("falsch")
        }
    }

    val textStore =
        store.sub(buildLens<Model, String>("text", Model::text, { p: Model, v: String -> p.copy(text = v) }))

    val gettingstarted = render {
        div {
            div("form-group") {
                label(`for` = store.id) {
                    text("Input")
                }
                input("form-control", id = store.id) {
                    placeholder = const("Add some input")
                    value = textStore.data

                    changes.values() handledBy textStore.update
                }
            }
            div("form-group") {
                label {
                    text("Value")
                }
                div("form-control") {
                    textStore.data.bind()
                    attr("readonly", "true")
                }
            }
            div("form-group") {
                button("btn btn-primary") {
                    text("Save")
                    clicks handledBy store.save
                }
                button("btn btn-primary") {
                    text("Load")
                    clicks handledBy store.load
                }
            }
        }
    }

    gettingstarted.mount("target")
}