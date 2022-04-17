import ClientReview from './ClientReview'

const reviews = [
  {id: 0, 
    advertisement: {
      id: 0,
      name: 'Praskozorje Vikendica',
      image: '/images/property_placeholder.jpg'
    },
    rating: 3,
    text: 'Bilo dobro ali onda nas napali drekavci. Lorem ipsum dolor sit amet consectetur adipisicing elit. Fuga magni animi repudiandae a totam dolores necessitatibus atque pariatur aut neque quo quod ratione maiores saepe sint reprehenderit ea, iure tempora quis quibusdam nam amet placeat incidunt. Officia, inventore quae totam placeat pariatur repellat deserunt quo aut corrupti illo aliquid hic?'
  },
  {id: 1, 
    advertisement: {
      id: 1,
      name: 'Limena Vikendica Zagoni',
      image: '/images/property_placeholder.jpg'
    },
    rating: 5,
    text: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Mollitia itaque maxime sunt libero. Cupiditate quisquam hic qui eaque voluptatem odit nemo quas molestiae, necessitatibus eius aspernatur adipisci, aliquam, aut quasi!'
  },
  {id: 2, 
    advertisement: {
      id: 2,
      name: 'Centar Beograda Savamala Dunavvelki',
      image: '/images/property_placeholder.jpg'
    },
    rating: 2,
    text: 'Lorem ipsum dolor sit amet consectetur, adipisicing elit. Repudiandae vitae totam ratione quaerat fugit, blanditiis reprehenderit odio eligendi libero voluptatem.'
  }
]

const ClientReviewList = () => {

  return ( 
    <div className="flex flex-col gap-y-6">
      {reviews.map((review) => 
        <ClientReview review={review} key={review.id}/>
      )}
    </div>
   );
}
 
export default ClientReviewList;