import { useState } from "react";
import { CSSTransition } from 'react-transition-group';
import ReactFlagsSelect from "react-flags-select";

const RegistrationPage = () => {

  const [currentSlide, setCurrentSlide] = useState(0);
  const [nextSlide, setNextSlide] = useState(0);
  const [previousSlide, setPreviousSlide] = useState(-1);

  const [selectedRole, setSelectedRole] = useState();
  const [selectedSubrole, setSelectedSubrole] = useState(null);
  const [selectedCountry, setSelectedCountry] = useState();

  const handleBack = () => {
    const c = currentSlide - 1 < 0 ? 0 : currentSlide - 1;
    setPreviousSlide(c + 1);
    setNextSlide(c);
    setCurrentSlide(-1);
    setTimeout(() => {
      setCurrentSlide(c);
    }, 125);
  }

  const handleNext = () => {
    const c = currentSlide + 1 > 3 ? 3 : currentSlide + 1;
    setPreviousSlide(c - 1);
    setNextSlide(c);
    setCurrentSlide(-1);
    setTimeout(() => {
      setCurrentSlide(c);
    }, 125);
  }

  return ( 
    <div className="flex flex-col justify-center min-h-screen w-full h-full
    first-letter:font-display bg-slate-500">
      <div className="flex flex-col justify-between h-160 sm:h-140 bg-white w-11/12 sm:w-5/6 md:w-120
      rounded-xl mx-auto overflow-hidden px-4 sm:px-10">
        <div className="text-left">
          <h1 className="mt-6 sm:mt-8 text-xl sm:text-2xl tracking-widest text-center">Create a New Account</h1>

          {/* step 1 */}
          <CSSTransition
          classNames="registration-first"
          timeout={100} 
          in={currentSlide === 0 && nextSlide === 0}
          unmountOnExit>
            <div>
              <h2 className="mt-4 sm:mt-1 font-bold text-lg tracking-widest text-center text-slate-800">Please select your role</h2>

              <div className="block sm:flex sm:flex-row sm:justify-center sm:gap-x-6 mt-6 sm:mt-10">

                <div onClick={() => setSelectedRole('client')}
                className={`flex gap-x-5 sm:block w-full sm:w-36 h-20 sm:h-36 p-4 rounded-xl border-2 border-slate-100 hover:border-slate-200
                cursor-pointer
                ${selectedRole === 'client' ? 'border-cyan-700 hover:border-cyan-700' : ''}`}>
                  <img src="/images/icons/person.png" alt="" className="sm:h-24 sm:mx-auto" />
                  <p className="font-display text-2xl sm:text-base text-center my-auto">Customer</p>
                </div>

                <div onClick={() => setSelectedRole('advertiser')} 
                className={`flex gap-x-5 sm:block w-full sm:w-36 h-20  sm:h-36 p-4 rounded-xl border-2 border-slate-100 hover:border-slate-200
                cursor-pointer mt-4 sm:mt-0
                ${selectedRole === 'advertiser' ? 'border-cyan-700 hover:border-cyan-700' : ''}`}>
                  <img src="/images/icons/building.png" alt="" className="sm:h-24 sm:mx-auto" />
                  <p className="font-display text-2xl sm:text-base text-center my-auto">Advertiser</p>
                </div>

              </div>
              
              
              <CSSTransition
              in={selectedRole === 'advertiser'}
              timeout={{enter: 30, exit: 150}}
              classNames="slide-fade"
              unmountOnExit
              >
                <div className="block sm:flex sm:flex-row sm:justify-center sm:gap-x-1 mt-3 sm:mt-6">

                  <div onClick={() => setSelectedSubrole('resort owner')}
                  className={`flex gap-x-5 sm:block w-full sm:w-18 h-14 sm:h-24 sm:py-4 rounded-xl border-2
                  border-slate-100 hover:border-slate-200 cursor-pointer
                  ${selectedSubrole === 'resort owner' ? 'border-cyan-700 hover:border-cyan-700' : ''}`}>
                    <img src="/images/icons/building.png" alt="" className="p-1.5 sm:p-0  sm:h-12 sm:mx-auto" />
                    <p className="font-display text-lg sm:text-sm text-center my-auto">Resort Owner</p>
                  </div>

                  <div onClick={() => setSelectedSubrole('boat owner')} 
                  className={`flex gap-x-5 sm:block w-full sm:w-18 h-14 sm:h-24 sm:py-4 rounded-xl border-2
                  border-slate-100 hover:border-slate-200 cursor-pointer mt-1 sm:mt-0
                  ${selectedSubrole === 'boat owner' ? 'border-cyan-700 hover:border-cyan-700' : ''}`}>
                    <img src="/images/icons/sailboat.png" alt="" className="p-1.5 sm:p-0 sm:h-12 sm:mx-auto" />
                    <p className="font-display text-lg sm:text-sm text-center my-auto">Boat Owner</p>
                  </div>
                  
                  <div onClick={() => setSelectedSubrole('instructor')} 
                  className={`flex gap-x-5 sm:block w-full sm:w-18 h-14 sm:h-24 sm:py-4 rounded-xl border-2
                  border-slate-100 hover:border-slate-200 cursor-pointer mt-1 sm:mt-0
                  ${selectedSubrole === 'instructor' ? 'border-cyan-700 hover:border-cyan-700' : ''}`}>
                    <img src="/images/icons/fisherman.png" alt="" className="p-1.5 sm:p-0 sm:h-12 sm:mx-auto" />
                    <p className="font-display text-lg sm:text-sm text-center my-auto">Instructor</p>
                  </div>
                </div>
              </CSSTransition>

            </div>
          </CSSTransition>

          {/* <!-- step 2 --> */}
          <CSSTransition
          classNames={`${previousSlide < nextSlide ? 'inter-first' : 'inter-second'}`}
          timeout={100} 
          in={currentSlide === 1 && nextSlide === 1}
          unmountOnExit>
            <div className="block mt-8">

              {/* <!-- email --> */}
              <label htmlFor="emailInput" className="text-xs text-slate-500">email:</label>
              <input id="emailInput" type="email" v-model="email" placeholder="email address"
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg
              focus:outline-none focus:border-gray-500 w-full caret-gray-700 py-1"/>
              <div className="h-2">
                {/* <p v-if="$v.email.$dirty && !$v.email.required" className="text-xs text-red-500 my-0">Field required.</p>
                <p v-if="$v.email.$dirty && !$v.email.email" className="text-xs text-red-500 my-0">Invalid email.</p> */}
              </div>

              {/* <!-- password --> */}
              <label className="text-xs text-slate-500">password:</label>
              <input type="password" v-model="password" placeholder="password"
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                {/* <p v-if="$v.password.$dirty && !$v.password.required" className="text-xs text-red-500 my-0">Field required.</p>
                <p v-if="$v.password.$dirty && !$v.password.minLength" className="text-xs text-red-500 my-0">
                  Password must have at least 6 characters.
                </p> */}
              </div>

              {/* <!-- repeat password --> */}
              <label className="text-xs text-slate-500">confirm password:</label>
              <input type="password" placeholder="confirm password"
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                {/* <p v-if="$v.repeatPassword.$dirty && !$v.repeatPassword.sameAsPassword" className="text-xs text-red-500 my-0">
                  Passwords do not match.
                </p> */}
              </div>
              
              {/* reason for advertiser account */}
              { selectedRole === 'advertiser' &&
              <div>
                <label className="text-xs text-slate-500">reason:</label>
                <textarea placeholder="I am a good candidate for receiving an advertisers account because..."
                rows={3}
                className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
                focus:outline-none focus:border-gray-500 w-full caret-gray-700 resize-none
                hidden-scrollbar"/>
                <div className="h-2">
                  {/* <p v-if="$v.repeatPassword.$dirty && !$v.repeatPassword.sameAsPassword" className="text-xs text-red-500 my-0">
                    Passwords do not match.
                  </p> */}
                </div>
              </div>
              }

            </div>
          </CSSTransition>

          {/* <!-- step 3 --> */}
          <CSSTransition
          classNames={`${previousSlide < nextSlide ? 'inter-first' : 'inter-second'}`}
          timeout={50}
          in={currentSlide === 2 && nextSlide === 2}
          unmountOnExit>
            <div className="block mt-8">

              {/* <!-- first name --> */}
              <label className="text-xs text-slate-500">first name:</label>
              <input placeholder="first name"
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                {/* <p v-if="$v.firstName.$dirty && !$v.firstName.required" className="text-xs text-red-500 my-0">Field required.</p>
                <p v-if="$v.firstName.$dirty && !$v.firstName.maxLength" className="text-xs text-red-500 my-0">
                  First name must be shorter than 30 characters.
                </p> */}
              </div>

              {/* <!-- last name --> */}
              <label className="text-xs text-slate-500">last name:</label>
              <input v-model="lastName" placeholder="last name"
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                {/* <p v-if="$v.lastName.$dirty && !$v.lastName.required" className="text-xs text-red-500 my-0">Field required.</p>
                <p v-if="$v.lastName.$dirty && !$v.lastName.maxLength" className="text-xs text-red-500 my-0">
                  Last name must be shorter than 30 characters.
                </p> */}
              </div>

              <label className="text-xs text-slate-500">phone number:</label>
              <input placeholder="phone number"
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                {/* <p v-if="$v.firstName.$dirty && !$v.firstName.required" className="text-xs text-red-500 my-0">Field required.</p>
                <p v-if="$v.firstName.$dirty && !$v.firstName.maxLength" className="text-xs text-red-500 my-0">
                  First name must be shorter than 30 characters.
                </p> */}
              </div>

            </div>
          </CSSTransition >


          {/* <!-- step 4 --> */}
          <CSSTransition
          className="second block mt-8"
          timeout={50}
          in={currentSlide === 3 && nextSlide === 3}
          unmountOnExit>
            <div className="block mt-8">

              <label className="text-xs text-slate-500">address:</label>
              <input placeholder="address"
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                {/* <p v-if="$v.firstName.$dirty && !$v.firstName.required" className="text-xs text-red-500 my-0">Field required.</p>
                <p v-if="$v.firstName.$dirty && !$v.firstName.maxLength" className="text-xs text-red-500 my-0">
                  First name must be shorter than 30 characters.
                </p> */}
              </div>

              <label className="text-xs text-slate-500">city:</label>
              <input placeholder="city"
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                {/* <p v-if="$v.firstName.$dirty && !$v.firstName.required" className="text-xs text-red-500 my-0">Field required.</p>
                <p v-if="$v.firstName.$dirty && !$v.firstName.maxLength" className="text-xs text-red-500 my-0">
                  First name must be shorter than 30 characters.
                </p> */}
              </div>

              <label className="text-xs text-slate-500">country:</label>
              <ReactFlagsSelect selectedSize={13} optionsSize={15} searchable={true}
                selected={selectedCountry}
                onSelect={(code) => setSelectedCountry(code)}
              />

            </div>
          </CSSTransition >
        </div>
        

        {/* buttons */}
        <div className="block w-full py-5">

          <div className="flex justify-center gap-x-3">

            {nextSlide !== 0 &&
            <button type="button" onClick={() => handleBack()}
            className="rounded-md px-6
            py-2 bg-white text-base font-medium text-slate-500 hover:text-slate-700
            hover:bg-slate-200 active:bg-slate-300
            focus:outline-none w-28 sm:text-sm">
            {"<+ back"}
            </button>
            }

            {nextSlide !== 3 &&
            <button type="button" onClick={() => handleNext()}
            className="rounded-md shadow-sm
            px-6 py-2 bg-cyan-700 text-base font-medium text-white
            hover:bg-cyan-800 active:bg-cyan-900 focus:outline-none
            w-28 sm:text-sm">
            next
            </button>
            }

            {nextSlide === 3 &&
            <button type="button" className="rounded-md
            shadow-sm px-6 py-2 bg-cyan-700 text-base font-medium text-white
            hover:bg-cyan-800 active:bg-cyan-900  focus:outline-none w-28
            sm:text-sm">
            sign up
            </button>
            }

          </div>
          {/* where am i */}
          <div className="flex justify-center gap-x-1 mt-4">
            {[0, 1, 2, 3].map(x => 
              <div key={x} className={`rounded-full w-3.5 h-3.5
              ${x < nextSlide ? "bg-cyan-700" : "bg-slate-300"}
              ${x === nextSlide ? "bg-cyan-600" : ""}`}
              ></div>
            )} 
          </div>

        </div>
        

      </div>
    </div>
   );
}
 
export default RegistrationPage;