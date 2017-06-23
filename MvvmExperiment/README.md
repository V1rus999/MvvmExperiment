# MVVMRxExperiment
An experiment which aims at implementing an VMMV-like structure into android. The idea is to have a single entry point back into the view from the viewmodel rather than multiple methods. The view needs to be state based and changes its behavior based on the state it receives.

The project has 3 components:

	* A normal MVVM like login component which doesn’t use Rx at all.
	* An Rx activity which compares a normal MVP flow to an Rx flow. This is more of a playground activity.
	* An Rx mvvm activity which uses a single entry point mvvm structure, but with Rx.

# Libraries Used

	* Butterknife
	* Dagger
	* Retrofit
	* View Animator
	* RxJava/RxAndroid

