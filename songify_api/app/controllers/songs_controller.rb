class SongsController < ApplicationController

	before_action :set_song, only: [:show, :update, :destroy]
	# GET /todos
  def index
  	@songs = paginate current_user.songs.unscoped, per_page: 5
  end

  # POST /songs
  def create
  	album = Album.find_by(title: params["album"])
  	artist = Artist.find_by(name: params["artist"])
  	p artist
  	@song = Song.new(title: params["title"], length: params["length"], genre: params["genre"], album: album)
    @song.artist = artist
    @song.save

    current_user.songs << @song
    response.set_header("Location", songs_path(@song))
    json_response(@song, :created)
  end

  # GET /song/:id
  def show
    json_response(@song)
  end

  # PUT /songs/:id
  def update
    @song.update(song_params)
    head :no_content
  end

  # DELETE /songs/:id
  def destroy
    @song.destroy
    head :no_content
  end

  private

  def song_params
    # whitelist params
    params.permit(:title, :length, :genre, :artist, :album, user: current_user)
  end

  def set_song
    @song = Song.find(params[:id])
  end

end
