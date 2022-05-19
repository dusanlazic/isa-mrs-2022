import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import schema from '../validators/loginSchema';
import { get, patch } from "../adapters/xhr";
import { getToken, getSession } from '../contexts'
import { logout } from '../util'; 

const AdminPasswordPage = () => {
  const [accountData, setAccountData] = useState(null);
  const session = getSession();
  const [errors, setErrors] = useState(null);
  const [password, setPassword] = useState(null);
  const [passwordConfirmation, setPasswordConfirmation] = useState(null);
  const [passwordConfirmationChanged, setPasswordConfirmationChanged] = useState(false)
  const navigate = useNavigate();

  useEffect(() => {
    get(`/api/account/whoami`)
    .then((response) => {
      setAccountData(response.data);
    })
    .catch((error) => {
      navigate('/');
    });
  }, [session.id])

  const changePassword = () => {
    patch('/api/admin/password', {
      password: password,
      passwordConfirmation: passwordConfirmation
    })
    .then((response) => {
      alert(response.data);
      logout();
      navigate(`/login`);
    })
    .catch((error) => {
      setErrors(error.response.data.errors);
    });
  }

  const clearErrors = () => {
    setErrors(null);
  }

  const logUserOut = () => {
    logout();
    navigate('/');
  }

  if (accountData === null || session === undefined) {
    return null;
  }

  return ( 
    <div className="flex flex-col justify-center min-h-screen w-full h-full
    px-8 sm:px-44 md:px-32 lg:px-48 xl:px-84 font-display bg-slate-500">
      <div className="block md:grid grid-cols-2 grid-rows-1 bg-white rounded-lg overflow-hidden h-140 my-auto shadow-sm">
        
        <img src="/images/login-side-8.jpg" alt="" className="h-52 md:h-full w-full object-cover"/>

        <div className="w-full md:h-full px-8 lg:px-6 xl:px-8 text-slate-500">
          {/* Greeting */}
          <div className="mt-6 text-left">
            <h1 className="text-2xl tracking-widest">Welcome, {accountData.firstName}</h1>
            <h3 className="text-lg">Please change your password before proceeding</h3>
          </div>

          <div className="block mt-6">
            <input type="password" placeholder="password"
            onChange={(event) => {setPassword(event.target.value); clearErrors();}}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300
            text-lg md:text-base lg:text-lg py-1 bg-slate-100 focus:outline-none focus:border-gray-500
            w-full caret-gray-700"
            autoComplete="off"/>
            <div className="h-2">
              <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.password}</p>
            </div>

            <input type="password" placeholder="confirm password" name="passwordConfirmation"
            onChange={(event) => {setPasswordConfirmation(event.target.value); clearErrors(); setPasswordConfirmationChanged(true)}}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 mt-2
            text-lg md:text-base lg:text-lg py-1 bg-slate-100 focus:outline-none
            focus:border-gray-500 w-full caret-gray-700"
            autoComplete="off"/>
            <div className="h-2">
              <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.passwordConfirmation}</p>
              <p className="text-xs text-red-500 my-0 tracking-wide">{password !== passwordConfirmation && passwordConfirmationChanged ? 'Passwords do not match.' : ''}</p>            
            </div>

            <div className="flex justify-end mt-16 md:mt-6"> 
              <input type="submit" value="Log out" className="inline-flex justify-center rounded-md shadow-sm
              px-6 py-2 bg-pine text-base font-medium text-gray-700 bg-gray-300 hover:bg-gray-400 active:bg-gray-500 cursor-pointer
              focus:outline-none w-full mr-3 md:w-auto sm:text-sm"
              onClick={logUserOut}/>
              <input type="submit" value="Change password" className="inline-flex justify-center rounded-md shadow-sm
              px-6 py-2 bg-pine text-base font-medium text-white hover:bg-cyan-800 cursor-pointer
              active:bg-cyan-900 focus:outline-none w-full md:w-auto sm:text-sm"
              onClick={changePassword}/>
            </div>
          </div>
        </div>
      </div>
    </div>
   );
}
 
export default AdminPasswordPage;