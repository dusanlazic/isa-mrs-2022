
const About = ({data}) => {

	let rules = data.rules.split(/\r?\n/);

	return (
		<div className="text-left text-gray-600">

			<div className="mb-5 text-xl text-black">Optional features</div>
			<div className="grid grid-cols-1">
				{data.options.map(x =>
					<span className="mb-2">&#8729; {x.name} ({x.maxCount}) - {x.description}</span>
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