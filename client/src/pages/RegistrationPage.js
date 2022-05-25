import { useState } from "react";
import { CSSTransition } from 'react-transition-group';
import ReactFlagsSelect from "react-flags-select";

import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { pageTwoSchema, pageThreeSchema, pageFourSchema } from '../validators/registrationSchema' 

import { registerCustomer, registerAdvertiser } from '../adapters/registration'

const RegistrationPage = () => {

  const [isRegistrationSuccessful, setIsRegistrationSuccessful] = useState(false);
  const [error, setError] = useState('');

  const [currentSlide, setCurrentSlide] = useState(0);
  const [nextSlide, setNextSlide] = useState(0);
  const [previousSlide, setPreviousSlide] = useState(-1);

  const [selectedRole, setSelectedRole] = useState('client');
  const [selectedSubrole, setSelectedSubrole] = useState(null);
  const [selectedCountry, setSelectedCountry] = useState('RS');
  const [reason, setReason] = useState('');

  const { register: registerTwo, handleSubmit: handleSubmitTwo,
    formState: { errors: errorsTwo }, clearErrors: clearErrorsTwo,
    getValues: getValuesTwo } = useForm({
    resolver: yupResolver(pageTwoSchema),
    mode: 'onSubmit',
    reValidateMode: 'onChange',
  });

  const { register: registerThree, handleSubmit: handleSubmitThree,
    formState: { errors: errorsThree }, clearErrors: clearErrorsThree,
    getValues: getValuesThree } = useForm({
    resolver: yupResolver(pageThreeSchema),
    mode: 'onSubmit',
    reValidateMode: 'onChange',
  });

  const { register: registerFour, handleSubmit: handleSubmitFour,
    formState: { errors: errorsFour }, clearErrors: clearErrorsFour,
    getValues: getValuesFour } = useForm({
    resolver: yupResolver(pageFourSchema),
    mode: 'onSubmit',
    reValidateMode: 'onChange',
  });

  const nextTwo = (data) => {
    handleNextAnimation();
  }

  const nextThree = (data) => {
    handleNextAnimation();
  }

  const submitAll = (data) => {
    if (selectedRole === 'client') {
      registerCustomer(
        {
          ...getValuesTwo(),
          ...getValuesThree(),
          ...getValuesFour(),
          selectedCountry
        },
        setIsRegistrationSuccessful,
        setError
      )
    }
    else {
      registerAdvertiser(
        {
          ...getValuesTwo(),
          ...getValuesThree(),
          ...getValuesFour(),
          selectedSubrole,
          reason,
          selectedCountry
        },
        setIsRegistrationSuccessful,
        setError
      )
    }
  }

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
    if (currentSlide === 0) {
      handleNextAnimation();
    }
  }

  const handleEnter = (e) => {
    if (e.key === 'Enter') {
      e.target.blur();
      handleSubmitTwo(nextTwo);
    }
  }

  const handleNextAnimation = () => {
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
    first-letter:font-display bg-slate-300">
      { /* used to be bg-slate-500 */}
      {!isRegistrationSuccessful && 
      <div className="flex flex-col justify-between h-160 sm:h-140 bg-white w-11/12 sm:w-5/6 md:w-120
      rounded-xl mx-auto overflow-hidden px-4 sm:px-10 shadow-sm">
        
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

                  <div onClick={() => {setSelectedRole('advertiser'); setSelectedSubrole('resort-owner')}} 
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

                    <div onClick={() => setSelectedSubrole('resort-owner')}
                    className={`flex gap-x-5 sm:block w-full sm:w-18 h-14 sm:h-24 sm:py-4 rounded-xl border-2
                    border-slate-100 hover:border-slate-200 cursor-pointer
                    ${selectedSubrole === 'resort-owner' ? 'border-cyan-700 hover:border-cyan-700' : ''}`}>
                      <img src="/images/icons/building.png" alt="" className="p-1.5 sm:p-0  sm:h-12 sm:mx-auto" />
                      <p className="font-display text-lg sm:text-sm text-center my-auto">Resort Owner</p>
                    </div>

                    <div onClick={() => setSelectedSubrole('boat-owner')} 
                    className={`flex gap-x-5 sm:block w-full sm:w-18 h-14 sm:h-24 sm:py-4 rounded-xl border-2
                    border-slate-100 hover:border-slate-200 cursor-pointer mt-1 sm:mt-0
                    ${selectedSubrole === 'boat-owner' ? 'border-cyan-700 hover:border-cyan-700' : ''}`}>
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
              <form autoComplete="off" id="registration-form-two" className="block mt-8" onSubmit={handleSubmitTwo(nextTwo)}>

                {/* <!-- email --> */}
                <label className="text-xs text-slate-500">email:</label>
                <input name="email" placeholder="email address"
                {...registerTwo('email')} onChange={() => {clearErrorsTwo(); setError('');}} onKeyPress={e => handleEnter(e)} 
                className={`block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg
                focus:outline-none focus:border-gray-500 w-full caret-gray-700 py-1
                ${errorsTwo.email?.message != null ? "border-red-700 focus:border-red-700" : ''}`}/>
                <div className="h-2 mb-2">
                  <p className="text-xs text-red-500 my-0">{errorsTwo.email?.message}</p>
                </div>

                {/* <!-- password --> */}
                <label className="text-xs text-slate-500">password:</label>
                <input name="password" type="password" placeholder="password"
                {...registerTwo('password')} onChange={() => {clearErrorsTwo(); setError('');}} onKeyPress={e => handleEnter(e)}
                className={`block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
                focus:outline-none focus:border-gray-500 w-full caret-gray-700
                ${errorsTwo.password?.message != null ? "border-red-700 focus:border-red-700" : ''}`}/>
                <div className="h-2 mb-2">
                  <p className="text-xs text-red-500 my-0">{errorsTwo.password?.message}</p>
                </div>

                {/* <!-- repeat password --> */}
                <label className="text-xs text-slate-500">confirm password:</label>
                <input name="confirmPassword" type="password" placeholder="confirm password"
                {...registerTwo('confirmPassword')} onChange={() => {clearErrorsTwo(); setError('');}} onKeyPress={e => handleEnter(e)}
                className={`block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
                focus:outline-none focus:border-gray-500 w-full caret-gray-700
                ${errorsTwo.confirmPassword?.message != null ? "border-red-700 focus:border-red-700" : ''}`}/>
                <div className="h-2 mb-2">
                  <p className="text-xs text-red-500 my-0">{errorsTwo.confirmPassword?.message}</p>
                </div>
                
                {/* reason for advertiser account */}
                { selectedRole === 'advertiser' &&
                <div>
                  <label className="text-xs text-slate-500">reason:</label>
                  <textarea placeholder="I am a good candidate for receiving an advertiser account because..."
                  rows={3} required onChange={event => {setReason(event.target.value); setError('');}} value={reason}
                  className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
                  focus:outline-none focus:border-gray-500 w-full caret-gray-700 resize-none
                  hidden-scrollbar"/>
                </div>
                }

                <input className="hidden" type="submit" />

              </form>
            </CSSTransition>

            {/* <!-- step 3 --> */}
            <CSSTransition
            classNames={`${previousSlide < nextSlide ? 'inter-first' : 'inter-second'}`}
            timeout={50}
            in={currentSlide === 2 && nextSlide === 2}
            unmountOnExit>
              <form autoComplete="off" id="registration-form-three" className="block mt-8" onSubmit={handleSubmitThree(nextThree)}>

                {/* <!-- first name --> */}
                <label className="text-xs text-slate-500">first name:</label>
                <input name="firstName" placeholder="first name"
                {...registerThree('firstName')} onChange={() => {clearErrorsThree(); setError('');}} onKeyPress={e => handleEnter(e)} 
                className={`block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
                focus:outline-none focus:border-gray-500 w-full caret-gray-700
                ${errorsThree.firstName?.message != null ? "border-red-700 focus:border-red-700" : ''}`}/>
                <div className="h-2 mb-2">
                  <p className="text-xs text-red-500 my-0">{errorsThree.firstName?.message}</p>
                </div>

                {/* <!-- last name --> */}
                <label className="text-xs text-slate-500">last name:</label>
                <input name="lastName" placeholder="last name"
                {...registerThree('lastName')} onChange={() => {clearErrorsThree(); setError('');}} onKeyPress={e => handleEnter(e)} 
                className={`block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
                focus:outline-none focus:border-gray-500 w-full caret-gray-700
                ${errorsThree.lastName?.message != null ? "border-red-700 focus:border-red-700" : ''}`}/>
                <div className="h-2 mb-2">
                  <p className="text-xs text-red-500 my-0">{errorsThree.lastName?.message}</p>
                </div>

                <label className="text-xs text-slate-500">phone number:</label>
                <input name="phoneNumber" placeholder="phone number"
                {...registerThree('phoneNumber')} onChange={() => {clearErrorsThree(); setError('');}} onKeyPress={e => handleEnter(e)} 
                className={`block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
                focus:outline-none focus:border-gray-500 w-full caret-gray-700
                ${errorsThree.phoneNumber?.message != null ? "border-red-700 focus:border-red-700" : ''}`}/>
                <div className="h-2">
                  <p className="text-xs text-red-500 my-0">{errorsThree.phoneNumber?.message}</p>
                </div>

                <input className="hidden" type="submit" />
              </form>
            </CSSTransition >


            {/* <!-- step 4 --> */}
            <CSSTransition
            className="second block mt-8"
            timeout={50}
            in={currentSlide === 3 && nextSlide === 3}
            unmountOnExit>
              <form autoComplete="off" id="registration-form-four" className="block mt-8" onSubmit={handleSubmitFour(submitAll)}>

                <label className="text-xs text-slate-500">address:</label>
                <input name="address" placeholder="address"
                {...registerFour('address')} onChange={() => {clearErrorsFour(); setError('');}} onKeyPress={e => handleEnter(e)}
                className={`block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
                focus:outline-none focus:border-gray-500 w-full caret-gray-700
                ${errorsFour.address?.message != null ? "border-red-700 focus:border-red-700" : ''}`}/>
                <div className="h-2 mb-2">
                  <p className="text-xs text-red-500 my-0">{errorsFour.address?.message}</p>
                </div>

                <label className="text-xs text-slate-500">city:</label>
                <input name="city" placeholder="city"
                {...registerFour('city')} onChange={() => {clearErrorsFour(); setError('');}} onKeyPress={e => handleEnter(e)}
                className={`block rounded-lg px-3 border text-gray-700 border-gray-300 text-lg py-1
                focus:outline-none focus:border-gray-500 w-full caret-gray-700
                ${errorsFour.city?.message != null ? "border-red-700 focus:border-red-700" : ''}`}/>
                <div className="h-2 mb-2">
                  <p className="text-xs text-red-500 my-0">{errorsFour.city?.message}</p>
                </div>

                <label className="text-xs text-slate-500">country:</label>
                <ReactFlagsSelect selectedSize={13} optionsSize={15} searchable={true}
                  selected={selectedCountry}
                  onSelect={(code) => setSelectedCountry(code)} required
                />

              <input className="hidden" type="submit" />
              </form>
            </CSSTransition >
          </div>
          

          {/* buttons */}
          <div className="block w-full py-5">
            
          <p className="text-sm text-red-500 my-0 tracking-wide">{error}</p>

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
              <button type="submit" onClick={() => handleNext()}
              className="rounded-md shadow-sm
              px-6 py-2 bg-cyan-700 text-base font-medium text-white
              hover:bg-cyan-800 active:bg-cyan-900 focus:outline-none
              w-28 sm:text-sm"
              form={currentSlide === 1 ? 'registration-form-two' : 'registration-form-three'}>
              next
              </button>
              }

              {nextSlide === 3 &&
              <button type="submit" className="rounded-md
              shadow-sm px-6 py-2 bg-cyan-700 text-base font-medium text-white
              hover:bg-cyan-800 active:bg-cyan-900  focus:outline-none w-28
              sm:text-sm"
              form="registration-form-four">
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
      }
      {
        isRegistrationSuccessful &&
        <div className="flex flex-col justify-center h-160 sm:h-140 bg-white w-11/12 sm:w-5/6 md:w-120
        rounded-xl mx-auto overflow-hidden px-4 sm:px-10">
          {
            selectedRole === 'client' &&
            <div className="mb-16">
              <img src="/images/icons/check-mail.png" alt="" className="h-32 sm:h-24 mx-auto mb-4" />
              <h1 className="text-2xl sm:text-3xl tracking-widest text-center">Welcome!</h1>
              <h3 className="text-lg sm:text-xl tracking-widest text-center mt-1">Please check your email address for the confirmation link.</h3>
            </div>
          }
          {
            selectedRole === 'advertiser' &&
            <div className="mb-16">
              <img src="/images/icons/inspection.png" alt="" className="h-32 sm:h-24 mx-auto mb-4" />
              <h1 className="text-2xl sm:text-3xl tracking-widest text-center">Success!</h1>
              <h3 className="text-lg sm:text-xl tracking-widest text-center mt-1">
                Our admin team will inspect your application, after which you will receive an email response.
              </h3>
            </div>
          }
        </div>
      }
    </div>
   );
}
 
export default RegistrationPage;