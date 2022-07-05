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
                "Platónn (en griego antiguo: Πλάτων, Plátōn; Atenas o Egina, c. 427-347 a. C.) fue un filósofo griego seguidor de Sócrates y maestro de Aristóteles. En 387 a. C. fundó la Academia de Atena institución que continuaría a lo largo de más de novecientos años y a la que Aristóteles acudiría desde Estagira a estudiar filosofía alrededor del 367 a. C., compartiendo unos veinte años de amistad y trabajo con su maestro."
            )
        )
        filosofos.add(
            Filosofia(
                "Maquiavelo",
                "Escuela de Florencia",
                "XV",
                "https://cdn.culturagenial.com/es/imagenes/nicolas-maquiavelo-cke.jpg",
                "Nicolás Maquiavelo (en italiano: Niccolò di Bernardo dei Machiavelli [nikkoˈlɔ makjaˈvɛlli]; Florencia; 3 de mayo de 1469 - Ibidem; 21 de junio de 1527) fue un diplomático, funcionario, filósofo político y escritor italiano, considerado padre de la Ciencia Política moderna. Fue así mismo una figura relevante del Renacimiento italiano. En 1513 escribió su tratado de doctrina política titulado El príncipe, póstumamente publicado en Roma en 1531."
            )
        )
        filosofos.add(
            Filosofia(
                "Spinoza",
                "Universidad Leiden",
                "XVII",
                "https://www.biografiasyvidas.com/biografia/s/fotos/spinoza.jpg",
                "Baruch Spinoza (Ámsterdam, 24 de noviembre de 1632 - La Haya, 21 de febrero de 1677) fue un filósofo neerlandés de origen sefardí hispano-portugués. También se le conoce como Baruj, Bento, Benito, Benedicto o Benedictus (de) Spinoza o Espinosa, según las distintas traducciones de su nombre basadas en las hipótesis sobre su origen. Heredero crítico del cartesianismo, es considerado uno de los tres grandes racionalistas de la filosofía del siglo xvii, junto al francés René Descartes y el alemán Gottfried Leibniz, con quien además tuvo una pequeña correspondencia."
            )
        )
        filosofos.add(
            Filosofia(
                "Descartes",
                "Varios",
                "XVI",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Frans_Hals_-_Portret_van_Ren%C3%A9_Descartes.jpg/800px-Frans_Hals_-_Portret_van_Ren%C3%A9_Descartes.jpg",
                "René Descartes (pronunciación en francés: /ʁəne dekaʁt/ ( escuchar); latinización: Renatus Cartesius; onomástico del que se deriva el adjetivo cartesiano; La Haye en Touraine, 31 de marzo de 1596-Estocolmo, 11 de febrero de 1650) fue un filósofo, matemático y físico francés considerado el padre de la geometría analítica y la filosofía moderna, así como uno de los protagonistas con luz propia en el umbral de la revolución científica"
            )
        )
    } */
}