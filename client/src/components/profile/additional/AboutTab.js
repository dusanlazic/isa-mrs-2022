
const About = ({data}) => {

	let rules = data.rules.split(/\r?\n/);

	return (
		<div className="flex flex-col gap-y-10 text-left text-gray-600">

      { data.options.length > 0 &&
      <div>
        <div className="mb-5 text-xl text-black">Optional features</div>
        <div className="grid grid-cols-1">
          {data.options.map((x, index) =>
          {
            return (
              <span key={index} className="mb-2">&#8729; {x.name} ({x.maxCount}) - {x.description}</span>
            )
          }
          )}
        </div>
      </div>}

			<div>
        <div className="mb-4 text-xl text-black">Rules</div>
        <div className="grid grid-cols-1">
          {rules.map((x, index) =>
            <span key={index} className="mb-2">&#8729; {x}</span>
          )}
        </div>
      </div>
		</div>
	);
}

export default About;