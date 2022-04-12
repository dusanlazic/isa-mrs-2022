import { useState } from "react";

const MainProfileInfoEditor = () => {

  const [image, setImage] = useState(null);
  const [imageFile, setImageFile] = useState(null);

  const uploadImage = () => {
    const [file] = document.getElementById('image-input').files;
    if (file) {
      setImageFile(file);
      setImage(URL.createObjectURL(file));
    }
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
          <input placeholder="first name"
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block">
          <label className="text-xs">last name:</label>
          <input placeholder="last name"
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-x-6 mt-1">

        <div className="block md:col-span-2 text-left">
          <label className="text-xs">email (immutable):</label>
          <input disabled readOnly value="email@example.com"
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>

        <div className="block text-left">
          <label className="text-xs">phone number:</label>
          <input placeholder="phone number" value="00123456789" onChange={() => {}}
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>
      </div>
      
      <div className="grid grid-cols-3 gap-x-6 mt-4">
        <button className="col-start-3 bg-teal-600 hover:bg-teal-700 active:bg-teal-800 drop-shadow-md
        text-white rounded-lg h-min py-2 lg:py-1.5 text-sm lg:text-base">
          Confirm changes
        </button>
      </div>

    </div>
   );
}
 
export default MainProfileInfoEditor;