const tags = [
	"Toilet", "Free toiletries", "Mosquito net", "Hairdryer", "TV", "WiFi", "Heating"
]

const rules = [
	"Climbing on the furniture is not allowed",
	"Please do not enter the property with shoes on",
	"No smoking",
	"Pets are not allowed",
	"Do NOT lose the key"
]



const About = () => {
	return (
		<div className="text-left text-gray-600">

			<div className="mb-5 text-xl text-black">Additional features</div>
			<div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-x-2 gap-y-2">
				{tags.map(x =>
					<span>&#10003; {x}</span>
				)}
			</div>

			<div className="mt-10 mb-5 text-xl text-black">Rules</div>
			<div className="grid grid-cols-1">
				{rules.map(x =>
					<span className="mb-2">&#8729; {x}</span>
				)}
			</div>
		</div>
	);
}

export default About;