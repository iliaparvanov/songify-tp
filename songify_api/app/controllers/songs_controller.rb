class SongsController < ApplicationController

	before_action :set_song, only: [:show, :update, :destroy]
	# GET /todos
  def index
    @songs = Song.all
    json_response(@songs)
  end

  # POST /songs
  def create
    @song = Song.create!(song_params)
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
    params.permit(:name, :title)
  end

  def set_song
    @song = Song.find(params[:id])
  end

end
