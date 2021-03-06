package com.example.eufirestoredap.viewModels

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eufirestoredap.Filosofia
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {
    private val db = Firebase.firestore
    private var _filosofos: MutableLiveData<ArrayList<Filosofia>> = MutableLiveData<ArrayList<Filosofia>>()
    internal var filoList: MutableList<Filosofia> = ArrayList()

    init {
        getFilosofos()
        listenFilosfos()
    }

     private fun listenFilosfos() {
        db.collection("Filosofos").addSnapshotListener {
                snapshot, e ->
            if(e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            if(snapshot != null) {
                val allFilosofos = ArrayList<Filosofia>()
                val documents = snapshot.documents
                documents.forEach {
                    val filosofo = it.toObject(Filosofia::class.java)
                    if(filosofo != null) {
                        allFilosofos.add(filosofo!!)
                    }
                }
                _filosofos.value = allFilosofos
            }
        }
    }

    internal var filosofos: MutableLiveData<ArrayList<Filosofia>>
        get() {return _filosofos}
        set(value) {_filosofos = value}

    private fun getFilosofos(){
        db.collection("Filosofos")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (filosofo in snapshot) {
                        filoList.add(filosofo.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    // RUN ONE TIME
    /* fun addFilo() {
        filosofos.add(
            Filosofia(
                "Platon",
                "Academia de Atenas",
                "IV a.C.",
                "https://www.biografiasyvidas.com/biografia/p/fotos/platon_2.jpg",
                "Plat??nn (en griego antiguo: ????????????, Pl??t??n; Atenas o Egina, c. 427-347 a. C.) fue un fil??sofo griego seguidor de S??crates y maestro de Arist??teles. En 387 a. C. fund?? la Academia de Atena instituci??n que continuar??a a lo largo de m??s de novecientos a??os y a la que Arist??teles acudir??a desde Estagira a estudiar filosof??a alrededor del 367 a. C., compartiendo unos veinte a??os de amistad y trabajo con su maestro."
            )
        )
        filosofos.add(
            Filosofia(
                "Maquiavelo",
                "Escuela de Florencia",
                "XV",
                "https://cdn.culturagenial.com/es/imagenes/nicolas-maquiavelo-cke.jpg",
                "Nicol??s Maquiavelo (en italiano: Niccol?? di Bernardo dei Machiavelli [nikko??l?? makja??v??lli]; Florencia; 3 de mayo de 1469 - Ibidem; 21 de junio de 1527) fue un diplom??tico, funcionario, fil??sofo pol??tico y escritor italiano, considerado padre de la Ciencia Pol??tica moderna. Fue as?? mismo una figura relevante del Renacimiento italiano. En 1513 escribi?? su tratado de doctrina pol??tica titulado El pr??ncipe, p??stumamente publicado en Roma en 1531."
            )
        )
        filosofos.add(
            Filosofia(
                "Spinoza",
                "Universidad Leiden",
                "XVII",
                "https://www.biografiasyvidas.com/biografia/s/fotos/spinoza.jpg",
                "Baruch Spinoza (??msterdam, 24 de noviembre de 1632 - La Haya, 21 de febrero de 1677) fue un fil??sofo neerland??s de origen sefard?? hispano-portugu??s. Tambi??n se le conoce como Baruj, Bento, Benito, Benedicto o Benedictus (de) Spinoza o Espinosa, seg??n las distintas traducciones de su nombre basadas en las hip??tesis sobre su origen. Heredero cr??tico del cartesianismo, es considerado uno de los tres grandes racionalistas de la filosof??a del siglo xvii, junto al franc??s Ren?? Descartes y el alem??n Gottfried Leibniz, con quien adem??s tuvo una peque??a correspondencia."
            )
        )
        filosofos.add(
            Filosofia(
                "Descartes",
                "Varios",
                "XVI",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Frans_Hals_-_Portret_van_Ren%C3%A9_Descartes.jpg/800px-Frans_Hals_-_Portret_van_Ren%C3%A9_Descartes.jpg",
                "Ren?? Descartes (pronunciaci??n en franc??s: /????ne deka??t/ ( escuchar); latinizaci??n: Renatus Cartesius; onom??stico del que se deriva el adjetivo cartesiano; La Haye en Touraine, 31 de marzo de 1596-Estocolmo, 11 de febrero de 1650) fue un fil??sofo, matem??tico y f??sico franc??s considerado el padre de la geometr??a anal??tica y la filosof??a moderna, as?? como uno de los protagonistas con luz propia en el umbral de la revoluci??n cient??fica"
            )
        )
    } */
}