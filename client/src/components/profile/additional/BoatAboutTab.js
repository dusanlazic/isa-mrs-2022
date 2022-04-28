
import { Icon } from '@iconify/react';

const BoatAbout = ({ data }) => {

	let rules = data.rules.split(/\r?\n/);

	return (
		<div className="text-left text-gray-600">

			<div className="grid grid-cols-1">

				<div className="flex mb-2">
					<Icon className="mr-2" icon="fluent:people-28-filled" inline={true} width="20" />
					Capacity: {data.capacity}
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="ic:baseline-directions-boat-filled" inline={true} width="20" />
					Type: {data.boatType}
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="bx:ruler" inline={true} width="20" />
					Length: {data.boatLength}
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="la:tachometer-alt" inline={true} width="20" />
					Speed: {data.boatSpeed}
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="mdi:engine" inline={true} width="20" />
					Engine number: {data.engineNumber}
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="bi:lightning-charge-fill" inline={true} width="20" />
					Engine power: {data.enginePower}
				</div>

			</div>

			<div className="mt-10 mb-5 text-xl text-black">Optional features</div>
			<div className="grid grid-cols-1">
				{data.options.map(x =>
					<span className="mb-2">&#8729; {x.name} ({x.maxCount}) - {x.description}</span>
				)}
			</div>

			<div className="mt-10 mb-5 text-xl text-black">Fishing equpment</div>
			<div className="grid grid-cols-1">
				{data.fishingEquipment.map(x =>
					<span className="mb-2">&#8729; {x.name}</span>
				)}
			</div>

			<div className="mt-10 mb-5 text-xl text-black">Navigational equipment</div>
			<div className="grid grid-cols-1">
				{data.navigationalEquipment.map(x =>
					<span className="mb-2">&#8729; {x.name}</span>
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

export default BoatAbout;