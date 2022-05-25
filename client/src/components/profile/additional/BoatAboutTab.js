
import { Icon } from '@iconify/react';

const BoatAbout = ({ data }) => {

	let rules = data.rules.split(/\r?\n/);

	return (
		<div className="text-left text-gray-600">

      <div className="mb-5 text-xl text-black">Main features</div>
			<div className="grid grid-cols-2 lg:grid-cols-3">

				<div className="flex mb-2">
					<Icon className="mr-2" icon="fluent:people-28-filled" inline={true} width="20" />
          <div className="break-all">Capacity: {data.capacity}</div>
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="ic:baseline-directions-boat-filled" inline={true} width="20" />
          <div className="break-all">Type: {data.boatType}</div>
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="bx:ruler" inline={true} width="20" />
					<div className="break-all">Length: {data.boatLength}</div>
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="la:tachometer-alt" inline={true} width="20" />
          <div className="break-all">Speed: {data.boatSpeed}</div>
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="mdi:engine" inline={true} width="20" />
          <div className="break-all">Num. of engines: {data.engineNumber}</div>
				</div>
				<div className="flex mb-2">
					<Icon className="mr-2" icon="bi:lightning-charge-fill" inline={true} width="20" />
          <div className="break-all">Engine power: {data.enginePower}</div>
				</div>

			</div>

			<div className="mt-10 mb-5 text-xl text-black">Optional features</div>
			<div className="grid grid-cols-1">
				{data.options.map((x, index) =>
					<span key={index} className="mb-2">&#8729; {x.name} ({x.maxCount}) - {x.description}</span>
				)}
			</div>

			<div className="mt-10 mb-5 text-xl text-black">Fishing equpment</div>
			<div className="grid grid-cols-1">
				{data.fishingEquipment.map((x, index) =>
					<span key={index} className="mb-2">&#8729; {x.name}</span>
				)}
			</div>

			<div className="mt-10 mb-5 text-xl text-black">Navigational equipment</div>
			<div className="grid grid-cols-1">
				{data.navigationalEquipment.map((x, index) =>
					<span key={index} className="mb-2">&#8729; {x.name}</span>
				)}
			</div>

			<div className="mt-10 mb-5 text-xl text-black">Rules</div>
			<div className="grid grid-cols-1">
				{rules.map((x, index) =>
					<span key={index} className="mb-2">&#8729; {x}</span>
				)}
			</div>
		</div>
	);
}

export default BoatAbout;