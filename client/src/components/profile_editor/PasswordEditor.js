import { useState } from 'react';
import { patch } from "../../adapters/xhr";

const PasswordEditor = () => {
  const [currentPassword, setCurrentPassword] = useState(null);
  const [newPassword, setNewPassword] = useState(null);
  const [passwordConfirmation, setPasswordConfirmation] = useState(null);
  const [passwordConfirmationChanged, setPasswordConfirmationChanged] = useState(false)

  const [errors, setErrors] = useState(null);

  const changePassword = () => {
    if (newPassword !== passwordConfirmation) {
      return;
    }

    patch('/api/account/password', {
      currentPassword: currentPassword,
      newPassword: newPassword,
      passwordConfirmation: passwordConfirmation
    })
    .then((response) => {
      alert(response.data);
    })
    .catch((error) => {
      if (error.response.data.errors !== undefined) {
        setErrors(error.response.data.errors);
      } else {
        if (error.response.data.status === 409) {
          setErrors({ newPassword: error.response.data.message })
        } else if (error.response.data.status === 403) {
          setErrors({ currentPassword: error.response.data.message })
        }
      }
    });
  }

  return ( 
    <div className="w-full">
      <div className="block pt-2 text-left w-full md:w-2/3 pr-3 lg:pr-0 lg:w-full ">
        <h1 className="text-2xl text-left text-gray-400 font-sans font-light">Password</h1>

        <div className="flex flex-col lg:grid grid-cols-2 gap-x-6">
          <div className="block col-span-1">
            <label className="text-xs">new password:
              <span className="text-xs text-red-500 ml-2 my-0 tracking-wide">{errors === null ? '' : errors.newPassword}</span>
            </label>
            <input placeholder="new password" type="password"
            onChange={(event) => setNewPassword(event.target.value)}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"
            autoComplete="new-password"/>
          </div>

          <div className="block col-span-1">
            <label className="text-xs">confirm password:
              <span className="text-xs text-red-500 ml-2 my-0 tracking-wide">
                {errors === null ? '' : errors.passwordConfirmation}
                {newPassword !== passwordConfirmation && passwordConfirmationChanged ? 'Passwords do not match.' : ''}
              </span>
            </label>
            <input placeholder="confirm password" type="password"
            onChange={(event) => {setPasswordConfirmation(event.target.value); setPasswordConfirmationChanged(true); }}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"
            autoComplete="new-password"/>
          </div>
        </div>

        <div className="grid grid-cols-3 gap-x-6 mt-2">
          <div className="text-left col-span-2">
            <label className="text-xs">current password:
              <span className="text-xs text-red-500 ml-2 my-0 tracking-wide">{errors === null ? '' : errors.currentPassword}</span>
            </label>
            <input placeholder="current password" type="password"
            onChange={(event) => setCurrentPassword(event.target.value)}
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"
            autoComplete="new-password"/>
          </div>

          <div className="flex flex-col justify-end md:col-start-3 text-left w-full">
            <button className="bg-red-500 hover:bg-red-600 active:bg-red-700 drop-shadow-md
            text-white rounded-lg h-min py-2 lg:py-1.5 text-sm lg:text-base"
            onClick={() => changePassword()}>
              Change password
            </button>
          </div>
        </div>    
      </div>
    </div>
   );
}
 
export default PasswordEditor;