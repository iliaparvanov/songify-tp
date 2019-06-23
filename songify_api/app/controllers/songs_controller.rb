class SongsController < ApplicationController

	before_action :set_song, only: [:show, :update, :destroy]
	# GET /todos
  def index
  	@songs = paginate Song.unscoped, per_page: 5
  end

  # POST /songs
  def create
  	album = Album.find_by(title: params["album"])
  	artist = Artist.find_by(name: params["artist"])
  	@song = Song.new(title: params["title"], length: params["length"], genre: params["genre"], album: album)
    @song.artist = artist
    @song.save!
    @current_user.songs << @song
    response.set_header("Location", song_url(@song))
    json_response(@song, :created)
  end

  # GET /song/:id
  def show
    json_response(@song)
  end

  # PUT /songs/:id
  def update
    album = Album.find_by(title: params["album"])
    artist = Artist.find_by(name: params["artist"])
    @song.update!(title: params["title"], length: params["length"], genre: params["genre"], album: album)
    @song.artist = artist
    @song.save!
    response.set_header("Location", song_url(@song))
    head :no_content
  end

  # DELETE /songs/:id
  def destroy
    @song.destroy!
    head :no_content
  end

  def my_songs
    @songs = paginate current_user.songs, per_page: 5
  end

  private

  def song_params
    params.permit(:title, :length, :genre, :artist, :album, user: current_user)
  end

  def set_song
    @song = Song.find(params[:id])
    if @song.nil?
      raise ActiveRecord::RecordNotFound
    end
  end

end
