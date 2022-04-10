

const Gallery = () => {
	return (
		<div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-x-2 gap-y-2">
			{[0, 1, 2, 3, 4, 5, 6, 7, 8, 9].map(x =>
				<img src="/images/property_placeholder.jpg" alt="" key={x}
					className="h-full rounded-md object-cover" />
			)}
		</div>
	);
}

export default Gallery;