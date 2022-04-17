import { useState } from "react";


const description = "A boat is a watercraft of a large range of types and sizes, but generally smaller than a ship, which is distinguished by its larger size, shape, cargo or passenger capacity, or its ability to carry boats. Small boats are typically found on inland waterways such as rivers and lakes, or in protected coastal areas.";

const BoatProfileMainInfo = ({data}) => {
	let [showMore, setShowMore] = useState(false);

	return (
		<div className="block md:flex bg-gray-100 rounded-lg p-10">
			<div className="block flex-none">
				<img src="images/property_placeholder.jpg" alt="" className="flex-none w-64 h-64 md:w-44 md:h-44 xl:w-52 xl:h-52 object-cover rounded-lg mx-auto" />
				<button className="text-gray-500
					bg-gray-200 hover:bg-gray-300 hover:text-gray-800 active:bg-transparent
					active:bg-gray-400 active:text-gray-50
					rounded-b-lg px-4 h-min text-base md:text-sm xl:text-base">Edit Profile</button>
			</div>
				
			<div className="flex flex-col flex-grow md:ml-4">

				<div className="block md:flex w-full justify-between text-center md:text-left">
					<h1 className="text-3xl md:text-2xl xl:text-3xl mt-4 mb-1 lg:mb-2 md:mt-1 tracking-tight my-auto">Yacht Platz</h1>
					<div className="block md:hidden text-lg text-center -mt-2">
						<div className="md:text-left -mt-1 text-sm text-gray-500">Service provider name</div>
						4.76 <span className="text-yellow-500 text-xl">&#9733;</span>
					</div>
				</div>


				<div className="hidden md:block text-lg text-left">
					<div className="md:text-left -mt-3 text-sm text-gray-500">Service provider name</div>
					4.76 <span className="text-yellow-500 text-xl">&#9733;</span>
				</div>

				{/* Description */}
				<div className="block mt-10 md:mt-2 text-left">
					<p className="text-justify text-gray-600 text-base md:text-sm xl:text-base">
						{showMore ? description : description.substring(0, 140)}{!showMore && description.length >= 140 ? '...' : ''}
					</p>

					{description.length >= 140 &&
						<button onClick={() => setShowMore(!showMore)} className="text-sm text-gray-400 hover:text-gray-600">
							{showMore ? 'Show less' : 'Show more'}
						</button>
					}
				</div>

			</div>
		</div>
	);
}

export default BoatProfileMainInfo;