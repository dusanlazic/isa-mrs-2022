import { appendErrors, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import schema from '../validators/loginSchema' 

const LoginPage = () => {

  const { register, handleSubmit, formState, clearErrors, reset} = useForm({
    resolver: yupResolver(schema),
    mode: 'onSubmit',
    reValidateMode: 'onChange',
  
  });
  const { errors } = formState;

  const logIn = (data) => {
    console.log(data);
  }

  const handleEnter = (e) => {
    if (e.key === 'Enter') {
      console.log("CKCKS")
      e.target.blur();
      handleSubmit(logIn)
    }
  }

  return ( 
    <div className="flex flex-col justify-center min-h-screen w-full h-full
    px-8 sm:px-44 md:px-32 lg:px-48 xl:px-84 font-display bg-slate-500">
      <div className="block md:grid grid-cols-2 grid-rows-1 bg-white rounded-lg overflow-hidden h-140 my-auto">

        <img src="/images/login-side-3.png" alt="" className="h-52 md:h-full w-full object-cover
        border-b border-cyan-800 border-opacity-30 md:border-none"/>

        <div className="w-full md:h-full px-8 lg:px-6 xl:px-8 text-slate-500">
          {/* Greeting */}
          <div className="mt-6 text-left md:text-center">
            <h1 className="text-2xl tracking-widest">Hello!</h1>
            <h3 className="text-lg tracking-widest">Welcome back.</h3>
          </div>

          <form className="block mt-6" onSubmit={handleSubmit(logIn)}>

            {/* EMAIL */}
            <input id="emailInput" placeholder="email address" name="email" {...register('email')} 
            onChange={() => {clearErrors()}} onKeyPress={e => handleEnter(e)}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300
            text-lg md:text-base lg:text-lg py-1 bg-slate-100 focus:outline-none focus:border-gray-500
            w-full caret-gray-700"/>
            <div className="h-2">
              <p className="text-xs text-red-500 my-0">{formState.errors.email?.message}</p>
            </div>

            {/* PASSWORD */}
            <input type="password" placeholder="password" name="password" {...register('password')} 
            onChange={() => {clearErrors()}} onKeyPress={e => handleEnter(e)}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 mt-2
            text-lg md:text-base lg:text-lg py-1 bg-slate-100 focus:outline-none
            focus:border-gray-500 w-full caret-gray-700"/>
            <div className="h-2">
              <p className="text-xs text-red-500 my-0">{formState.errors.password?.message}</p>
            </div>

            {/* main error */}
            <div className="h-2">
              {/* <p className="text-xs text-red-500 tracking-wide"></p> */}
            </div>

            <div className="flex justify-end mt-16 md:mt-2">
              <input type="submit" value="Sign In" className="inline-flex justify-center rounded-md shadow-sm
              px-6 py-2 bg-cyan-700 text-base font-medium text-white hover:bg-cyan-800
              active:bg-cyan-900 focus:outline-none w-full md:w-auto sm:text-sm"/>
            </div>

          </form>

        </div>
      </div>
    </div>
   );
}
 
export default LoginPage;