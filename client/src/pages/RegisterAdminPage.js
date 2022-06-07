import { useState } from "react";
import { post } from "../adapters/xhr";
import { useNavigate } from 'react-router-dom';

const RegisterAdminPage = () => {
  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [username, setUsername] = useState(null);
  const [password, setPassword] = useState(null);
  const [passwordConfirmation, setPasswordConfirmation] = useState(null);

  const navigate = useNavigate();
  const [errors, setErrors] = useState(null);
  const [passwordConfirmationChanged, setPasswordConfirmationChanged] = useState(false)

  const registerAdmin = () => {
    post('/api/admin/register', {
      firstName: firstName,
      lastName: lastName,
      username: username,
      password: password,
      passwordConfirmation: passwordConfirmation
    })
    .then((response) => {
      navigate('/')
    })
    .catch((error) => {
      setErrors(error.response.data.errors);
    });
  }

  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">
      <div className="flex flex-col divide-y divide-dashed
       h-full border-0.5 rounded-lg gap-y-4">
        <div className="block w-full">
          <h1 className="text-2xl text-left text-gray-400 font-sans">Add a new administrator</h1>
          <div className="grid grid-cols-6 mt-2 gap-x-6">
            <div className="block col-span-2 text-left">
              <label className="text-xs">First name</label>
              <input placeholder="first name"
              onChange={(event) => {setFirstName(event.target.value)}}
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.firstName}</p>
              </div>
            </div>

            <div className="block col-span-2 text-left">
              <label className="text-xs">Last name</label>
              <input placeholder="last name"
              onChange={(event) => {setLastName(event.target.value)}}
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.lastName}</p>
              </div>
            </div>
          </div>

          <div className="grid grid-cols-6 mt-2">
            <div className="block col-span-4 text-left">
              <label className="text-xs">Email</label>
              <input placeholder="email"
              autoComplete="false"
              onChange={(event) => {setUsername(event.target.value)}}
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.username}</p>
              </div>
            </div>
          </div>

          <div className="grid grid-cols-3 mt-2">
            <div className="block col-span-2 text-left">
              <label className="text-xs">Password</label>
              <input placeholder="password"
              type="password"
              autocomplete="new-password"
              onChange={(event) => {setPassword(event.target.value)}}
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.password}</p>
              </div>
            </div>
          </div>

          <div className="grid grid-cols-3 mt-2">
            <div className="block col-span-2 text-left">
              <label className="text-xs">Confirm password</label>
              <input placeholder="confirm password"
              type="password"
              autoComplete="new-password"
              onChange={(event) => {setPasswordConfirmation(event.target.value); setPasswordConfirmationChanged(true);}}
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.passwordConfirmation}</p>
                <p className="text-xs text-red-500 my-0 tracking-wide">{password !== passwordConfirmation && passwordConfirmationChanged ? 'Passwords do not match.' : ''}</p>
              </div>
            </div>
          </div>

          <div className="grid grid-cols-4">
            <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 drop-shadow-md
              text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base mb-1.5 mt-12"
              onClick={() => {registerAdmin()}}>
                Register administrator
            </button>
          </div>
        </div>
      </div>

    </div>
   );
}
 
export default RegisterAdminPage;