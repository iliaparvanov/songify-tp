Rails.application.routes.draw do
  resources :artists do
    resources :albums
  end

  get 'albums', to: 'albums#all_albums'

  get 'songs/my_songs', to: 'songs#my_songs'

  resources :songs do
  end
  
  post 'auth/login', to: 'authentication#authenticate'
  post 'signup', to: 'users#create'
end
