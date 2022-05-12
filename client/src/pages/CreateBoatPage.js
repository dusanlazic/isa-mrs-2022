import BoatInfoEditor from "../components/boat/BoatInfoEditor";

const CreateBoatPage = () => {
  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">

      <div className="flex flex-col divide-y divide-dashed h-full border-0.5 rounded-lg gap-y-4">
        <BoatInfoEditor/>
      </div>

    </div>
   );
}
 
export default CreateBoatPage;