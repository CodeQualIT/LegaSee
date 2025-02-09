package nl.cqit.legasee

object AncestorTree {
    data class Person(
        val personalInfo: PersonalInfo,
        val parents: List<ParentalFigure>,
    )
    data class PersonalInfo(
        val imageURI: String?,
        val firstName: String,
        val lastName: String,
        val dateOfBirth: String,
        val placeOfBirth: String? = null,
        val dateOfDeath: String? = null,
        val placeOfDeath: String? = null,
    ) {
        val fullName: String
            get() = "$firstName $lastName"
    }
    data class ParentalFigure(
        val parent: Person,
        val type: ParentalFigureType,
        val legalParent: Boolean = true,
        val secondaryTypes: List<ParentalFigureType> = emptyList(),
    )
    sealed interface ParentalFigureType {
        val type: String
        data object Father : ParentalFigureType {
            override val type: String = "father"
        }
        data object Mother : ParentalFigureType {
            override val type: String = "mother"
        }
        data object BiologicalFather : ParentalFigureType {
            override val type: String = "biological father"
        }
        data object BiologicalMother : ParentalFigureType {
            override val type: String = "biological mother"
        }
        data object StepFather : ParentalFigureType {
            override val type: String = "step father"
        }
        data object StepMother : ParentalFigureType {
            override val type: String = "step mother"
        }
        data object Guardian : ParentalFigureType {
            override val type: String = "guardian"
        }
        data object FosterParent : ParentalFigureType {
            override val type: String = "foster parent"
        }
        data object AdoptiveFather : ParentalFigureType {
            override val type: String = "adoptive father"
        }
        data object AdoptiveMother : ParentalFigureType {
            override val type: String = "adoptive mother"
        }
        data object GodParent : ParentalFigureType {
            override val type: String = "godparent"
        }
        data object SurrogateMother : ParentalFigureType {
            override val type: String = "surrogate mother"
        }
        data object DonorParent : ParentalFigureType {
            override val type: String = "donor parent"
        }
        interface CustomParentalFigureType : ParentalFigureType
    }
}
