
const PasswordEditor = () => {

  return ( 
    <div className="w-full">
      <div className="block pt-2 text-left w-full md:w-2/3 pr-3 lg:pr-0 lg:w-full ">
        <h1 className="text-2xl text-left text-gray-400 font-sans font-light">Password</h1>

        <div className="block lg:w-1/2">
          <label className="text-xs">current password:</label>
          <input placeholder="current password" type="password"
          className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
          focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
        </div>


        <div className="flex flex-col lg:grid grid-cols-3 gap-x-6">

          <div className="block">
            <label className="text-xs">new password:</label>
            <input placeholder="new password" type="password"
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
          </div>

          <div className="block">
            <label className="text-xs">confirm password:</label>
            <input placeholder="confirm password" type="password"
            className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-1
            focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
          </div>

          <div className="flex flex-col justify-end mt-4 lg:mt-0">
            <button className="bg-red-500 hover:bg-red-600 active:bg-red-700 drop-shadow-md
            text-white rounded-lg h-min py-2 lg:py-1.5 text-sm lg:text-base">
              Change password
            </button>
          </div>
        </div>
        
      </div>
    </div>
   );
}
 
export default PasswordEditor;