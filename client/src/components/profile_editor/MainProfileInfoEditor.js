import { useState } from "react";
import ReactFlagsSelect from "react-flags-select";
import { put } from "../../adapters/xhr";
import { getWhoAmI } from "../../adapters/login";

import MessageModal from "../modals/MessageModal";

const MainProfileInfoEditor = ({data, refreshData}) => {

  const [image, setImage] = useState(null);
  const [imageFile, setImageFile] = useState(null);
  const [firstName, setFirstName] = useState(data.firstName);
  const [lastName, setLastName] = useState(data.lastName);
  const [phoneNumber, setPhoneNumber] = useState(data.phoneNumber);
  const [city, setCity] = useState(data.city);
  const [address, setAddress] = useState(data.address);
  const [selectedCountry, setSelectedCountry] = useState(data.countryCode);

  const [showMessageModal, setShowMessageModal] = useState(false);
  const [messageModalText, setMessageModalText] = useState('');

  const uploadImage = () => {
    const [file] = document.getElementById('image-input').files;
    if (file) {
      setImageFile(file);
      setImage(URL.createObjectURL(file));
    }
  }

  const updateAccount = () => {
    put(`/api/account`, {id: data.id, firstName, lastName, phoneNumber, city, address,
      countryCode: selectedCountry, emailAddress:data.emailAddress})
    .then(response => {
      getWhoAmI();
      setMessageModalText('Changes saved successfully!');
      setShowMessageModal(true);
      refreshData();
    })
    .catch(error => {
      setMessageModalText(error.response.data.message);
      setShowMessageModal(true);
    });
  }

  return ( 
    <div className="block w-full">
      <h1 className="text-2xl text-left text-gray-400 font-sans font-light">Main profile information</h1>

      {/* profile image */}
      <div className="block mt-4">
        <div className="flex rounded-lg w-full ml-1">
          <label id="label-input" className="outline-dashed outline-2 outline-offset-2 outline-gray-400
            hover:outline-gray-600
          hover:border-gray-300 cursor-pointer">

            <img id="image-preview" src={image != null ? image : 'images/fish_guy.jpg'}
            className="flex-none w-24 h-24 rounded-xl object-cover"/>

            <input type="file" accept="image/*" onChange={() => uploadImage()} id="image-input" 
            className="opacity-0 hidden h-0 w-0"/>

          </label>
        </div>
      </div>

      {/* name and surname */}
      <div className="grid grid-cols-1 md:grid-cols-2 text-left mt-6 gap-x-6">

        <div className="block">
          <label className="text-xs">first name:</label>
          <input placeholder="first name" value={firstName ? firstName : ''}
          onChange={(event) => {setFirstName(event.target.value)}}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block">
          <label className="text-xs">last name:</label>
          <input placeholder="last name" value={lastName ? lastName : ''}
          onChange={(event) => {setLastName(event.target.value)}}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

      </div>

      {/* email and phone number */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-x-6 mt-1">

        <div className="block md:col-span-2 text-left">
          <label className="text-xs">email (immutable):</label>
          <input disabled readOnly value={data.username ? data.username : ''}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block text-left">
          <label className="text-xs">phone number:</label>
          <input placeholder="phone number" value={phoneNumber ? phoneNumber : ''}
          onChange={(event) => {setPhoneNumber(event.target.value)}}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>

      {/* city and address */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-x-6 mt-1">

        <div className="block text-left">
          <label className="text-xs">city:</label>
          <input placeholder="city" value={city ? city : ''}
          onChange={(event) => {setCity(event.target.value)}}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block md:col-span-2 text-left">
          <label className="text-xs">address:</label>
          <input placeholder="address" value={address ? address : ''} 
          onChange={(event) => {setAddress(event.target.value)}}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

      </div>

      {/* country and confirm button */}
      <div className="grid grid-cols-1 md:grid-cols-3 md:gap-x-6 mt-4">

        <div className="col-span-3 md:col-span-2 lg:col-span-1 text-left">
          <label className="text-xs">country:</label>
          <ReactFlagsSelect selectedSize={13} optionsSize={15} searchable={true}
            selected={selectedCountry}
            onSelect={(code) => setSelectedCountry(code)}
          />
        </div>

        <div className="flex flex-col justify-end md:col-start-3 text-left w-full">
          <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 w-full drop-shadow-md
          text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base mb-1.5 mt-3 md:mt-0"
          onClick={() => {updateAccount()}}>
            Confirm changes
          </button>
        </div>

      </div>

      {showMessageModal && <MessageModal  closeFunction = {() => setShowMessageModal(false)}
                                    text = { messageModalText }
        	/>}

    </div>
   );
}
 
export default MainProfileInfoEditor;